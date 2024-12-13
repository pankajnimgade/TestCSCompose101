package com.example.csvcompose101.view

import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.content.Intent.createChooser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.csvcompose101.R
import com.example.csvcompose101.data.models.Item
import com.example.csvcompose101.ui.theme.CSVCompose101Theme
import com.example.csvcompose101.viewmodels.DetailPhotoActivityViewModel
import com.example.csvcompose101.viewmodels.SampleData


class DetailPhotoActivity : ComponentActivity() {

    val viewModel: DetailPhotoActivityViewModel by viewModels<DetailPhotoActivityViewModel>()

    companion object {
        const val KEY_ITEM = "KEY_ITEM"
        const val KEY_SHARE_TYPE = "text/plain"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initializeItem(intent.getStringExtra(KEY_ITEM))
        enableEdgeToEdge()
        setContent {
            CSVCompose101Theme {
                Scaffold(topBar = {
                    TopAppBar(title = { Text(text = stringResource(R.string.details)) },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.back)
                                )
                            }
                        })
                }, floatingActionButton = {
                    ShareFloatingActionButton(viewModel.item.value) {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.setType(KEY_SHARE_TYPE)
                        intent.putExtra(EXTRA_TEXT, viewModel.getImageDetailsToShare())
                        this@DetailPhotoActivity.startActivity(
                            createChooser(
                                intent, getString(R.string.share)
                            )
                        )

                    }
                }) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        DetailPhotoScreen(viewModel)
                    }
                }
            }
        }
    }

    @Composable
    private fun DetailPhotoScreen(viewModel: DetailPhotoActivityViewModel) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ShowCurrentImage(viewModel.item.value)
            Spacer(modifier = Modifier.size(10.dp))
            ImageTitle(viewModel.item.value, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.size(10.dp))
            ImageDescription(viewModel.item.value, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.size(10.dp))
            ImageAuthor(viewModel.item.value, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.size(10.dp))
            ShowPublishedDate(viewModel.item.value, modifier = Modifier.padding(8.dp))
        }
    }

    private @Composable
    fun ImageTitle(item: Item, modifier: Modifier = Modifier) {
        val currentTitle = if (item.title.isBlank()) {
            "Missing Title"
        } else {
            item.title
        }
        Text(
            text = "Title: $currentTitle",
            modifier = modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.titleLarge
        )
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
    @Composable
    fun ShowPreviewOfImageTitle() {
        Surface(modifier = Modifier.padding(8.dp)) {
            ImageTitle(SampleData.getIndividualItem())
        }
    }


    private @Composable
    fun ImageDescription(item: Item, modifier: Modifier = Modifier) {
        val currentDescription = viewModel.getDescription()
        Text(
            text = "Description: $currentDescription",
            modifier = modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
    @Composable
    fun ShowPreviewOfImageDescription() {
        Surface(modifier = Modifier.padding(8.dp)) {
            ImageDescription(SampleData.getIndividualItem())
        }
    }

    private @Composable
    fun ImageAuthor(item: Item, modifier: Modifier = Modifier) {
        val currentAuthor = viewModel.getAuthor()
        Text(
            text = "Author: $currentAuthor",
            modifier = modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
    @Composable
    fun ShowPreviewOfImageAuthor() {
        Surface(modifier = Modifier.padding(8.dp)) {
            ImageAuthor(SampleData.getIndividualItem())
        }
    }

    private @Composable
    fun ShowCurrentImage(item: Item) {
        Surface(
            shape = MaterialTheme.shapes.medium, modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            AsyncImage(
                model = item.media?.m ?: stringResource(R.string.url_place_holder),
                contentDescription = item.description,
                contentScale = ContentScale.Crop
            )
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
    @Composable
    fun ShowPreviewOfShowCurrentImage() {
        Surface(modifier = Modifier.padding(8.dp)) {
            ShowCurrentImage(SampleData.getIndividualItem())
        }
    }

    /** ShowPublishedDate start ***/
    private @Composable
    fun ShowPublishedDate(item: Item, modifier: Modifier = Modifier) {
        val currentDate = viewModel.getFormattedDate()
        Surface(
            shape = MaterialTheme.shapes.medium, modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Text(
                text = "Date: $currentDate",
                modifier = modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
    @Composable
    fun ShowPreviewOfShowPublishedDate() {
        Surface(modifier = Modifier.padding(8.dp)) {
            ShowPublishedDate(SampleData.getIndividualItem())
        }
    }

    /** ShowPublishedDate end ***/

    @Composable
    fun ShareFloatingActionButton(item: Item, onClick: () -> Unit) {
        SmallFloatingActionButton(
            onClick = { onClick() },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,

            ) {
            Icon(Icons.Filled.Share, stringResource(R.string.share_image_information))
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
    @Composable
    fun ShowPreviewOfShareFloatingActionButton() {
        Surface(modifier = Modifier.padding(8.dp)) {
            ShareFloatingActionButton(item = SampleData.getIndividualItem()) {

            }
        }
    }
}
