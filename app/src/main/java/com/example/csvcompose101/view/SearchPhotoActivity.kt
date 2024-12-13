package com.example.csvcompose101.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.csvcompose101.R
import com.example.csvcompose101.data.models.Item
import com.example.csvcompose101.ui.theme.CSVCompose101Theme
import com.example.csvcompose101.viewmodels.MainActivityViewModel
import com.example.csvcompose101.viewmodels.SampleData
import com.google.gson.Gson

class SearchPhotoActivity : ComponentActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivityViewModel.searchImageForText()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CSVCompose101Theme {
                SearchScreen(mainActivityViewModel) { item ->
                    val intent = Intent(this@SearchPhotoActivity, DetailPhotoActivity::class.java)
                    intent.putExtra(DetailPhotoActivity.KEY_ITEM, (Gson().toJson(item)))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    this@SearchPhotoActivity.startActivity(intent)
                }
            }
        }
    }


    /** SearchScreen Start ****************************************/
    @Composable
    fun SearchScreen(
        viewModel: MainActivityViewModel = MainActivityViewModel(),
        modifier: Modifier = Modifier,
        OnCardClick: (Item) -> Unit
    ) {
        Scaffold(modifier = Modifier) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar() {
                    viewModel.searchImageForText(text = it)
                }
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                )
                ShowGridOfPhotos(modifier = Modifier.fillMaxSize(), viewModel, OnCardClick)
            }
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
    @Composable
    fun ShowPreviewOfSearchScreen() {
        SearchScreen() {

        }
    }

    /** SearchScreen end ****************************************/


    /** SearchBar Start ****************************************/
    @Composable
    private fun SearchBar(modifier: Modifier = Modifier, searchCallBack: (String) -> Unit) {
        var inputText by rememberSaveable { mutableStateOf("") }
        TextField(value = inputText,
            onValueChange = {
                inputText = it
                searchCallBack(inputText)
            },
            modifier = modifier
                .heightIn(min = 56.dp)
                .padding(16.dp)
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = ""
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            placeholder = {
                Text(text = stringResource(R.string.placeholder_search))
            })
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
    @Composable
    fun ShowPreviewOfSearchBar() {
        Surface(modifier = Modifier.padding(8.dp)) {
            SearchBar() {
                // don't do anything
            }
        }
    }
    /** SearchBar End ****************************************/


    /** ShowGridOfPhotos Start ****************************************/
    @Composable
    fun ShowGridOfPhotos(
        modifier: Modifier = Modifier,
        viewModel: MainActivityViewModel = MainActivityViewModel(),
        OnCardClick: (Item) -> Unit
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.heightIn(150.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
        ) {
            items(viewModel.list) {
                IndividualImageCard(it, modifier = modifier, OnCardClick)
            }
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
    @Composable
    fun ShowPreviewOfShowGridOfPhotos() {
        ShowGridOfPhotos(viewModel = MainActivityViewModel()) {

        }
    }

    /** ShowGridOfPhotos end ****************************************/


    /** AlignYourBodyElement Start ****************************************/
    @Composable
    fun IndividualImageCard(
        item: Item = SampleData.getIndividualItem(),
        modifier: Modifier = Modifier,
        OnCardClick: (Item) -> Unit
    ) {

        Column(
            modifier = Modifier
                .clickable {
                    OnCardClick(item)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = item.media?.m,
                contentDescription = null,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = item.tags,
                modifier = Modifier
                    .paddingFromBaseline(top = 24.dp, bottom = 8.dp)
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
    @Composable
    fun ShowPreviewOfIndividualImageCard() {
        IndividualImageCard(modifier = Modifier.padding(8.dp)) {
            // do nothing
        }
    }

    /** AlignYourBodyElement end ****************************************/
}

