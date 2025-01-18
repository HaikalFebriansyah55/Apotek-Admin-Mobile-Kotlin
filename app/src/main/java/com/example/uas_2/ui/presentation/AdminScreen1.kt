package com.example.uas_2.ui.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_2.retrofit.ObatRequest
import com.example.uas_2.retrofit.ObatResponseItem
import com.example.uas_2.retrofit.ObatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen1(
    viewModel : ObatViewModel = viewModel()
) {
    val obatList by viewModel.obatList.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState("")
    var isDataChanged by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var selectedObat by remember { mutableStateOf<ObatResponseItem?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator()
            }

            errorMessage.isNotEmpty() -> {
                Text(
                    text = errorMessage
                )
            }

            obatList.isNotEmpty() -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(obatList.size) {
                        val obat = obatList[it]
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            onClick = {
                                selectedObat = obat
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(text = "Nama : ${obat.nama}")
                                Text(text = "Harga : ${obat.harga}")
                                Text(text = "Jenis : ${obat.jenis}")
                                Text(text = "Deskripsi : ${obat.deskripsi}")
                            }
                        }
                    }
                }
            }
        }
        selectedObat?.let { obat ->
            var id by remember { mutableStateOf(obat.id) }
            var nama by remember { mutableStateOf(obat.nama ?: "") }
            var harga by remember { mutableStateOf(obat.harga.toString()) }
            var dosis by remember { mutableStateOf(obat.dosis ?: "") }
            var jenis by remember { mutableStateOf(obat.jenis ?: "") }
            var deskripsi by remember { mutableStateOf(obat.deskripsi ?: "") }

            AlertDialog(
                onDismissRequest = { selectedObat = null },
                confirmButton = {
                    TextButton(onClick = { selectedObat = null }
                    ) {
                        Text("Tutup")
                    }
                },
                title = {
                    Text("Detail Obat")
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        TextField(
                            value = nama,
                            onValueChange = {
                                nama = it
                                isDataChanged = true
                            },
                            label = { Text("Nama Obat") },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        TextField(
                            value = harga,
                            onValueChange = {
                                harga = it
                                isDataChanged = true
                            },
                            label = { Text("Harga Obat") },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        TextField(
                            value = dosis,
                            onValueChange = {
                                dosis = it
                                isDataChanged = true
                            },
                            label = { Text("Jenis Obat") },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        TextField(
                            value = jenis,
                            onValueChange = {
                                jenis = it
                                isDataChanged = true
                            },
                            label = { Text("Jenis Obat") },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        TextField(
                            value = deskripsi,
                            onValueChange = {
                                deskripsi = it
                                isDataChanged = true
                            },
                            label = { Text("Deskripsi Obat") },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                },
                dismissButton = {
                    if (isDataChanged) {
                        TextButton(onClick = {
                            id?.let {
                                viewModel.updateObat(
                                    id = it,
                                    updatedObat = ObatRequest(
                                        nama = nama,
                                        dosis = dosis,
                                        harga = harga.toDouble(),
                                        jenis = jenis,
                                        deskripsi = deskripsi
                                    )
                                )
                            }
                            selectedObat = null
                            Toast.makeText(context, "Data Updated", Toast.LENGTH_SHORT).show()
                        }) {
                            Text("Update")
                        }
                    }
                    TextButton(onClick = {
                        id?.let {
                            viewModel.deleteObat(it)
                        }
                        selectedObat = null
                    }) {
                        Text("Delete Data ini")
                    }
                }
            )
        }
    }
}