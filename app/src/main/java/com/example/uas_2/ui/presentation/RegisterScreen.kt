package com.example.uas_2.ui.presentation

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uas_2.R
import com.example.uas_2.navigation.Screen
import com.example.uas_2.retrofit.ObatRequest
import com.example.uas_2.retrofit.ObatViewModel

@Composable
fun RegisterScreen(
    viewModel : ObatViewModel = viewModel(),
    navController: NavController
) {

    var nama by remember { mutableStateOf("") }
    var harga by remember { mutableStateOf("") }
    var dosis by remember { mutableStateOf("") }
    var jenis by remember { mutableStateOf("") }
    var gambar by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState("")
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            "Create Data Obat",
            fontSize = 30.sp
        )
        Image(
            painter = painterResource(R.drawable.icon1)
            , contentDescription = "",
            modifier = Modifier
                .size(100.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(7.dp),
        ){
            Text(
                "Nama Obat",
            )
            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it },
                label = { Text("Masukkan Nama Obat") }
            )
            Text("Harga Obat")
            OutlinedTextField(
                value = harga,
                onValueChange = { harga = it },
                label = { Text("Masukkan Harga Obat") }
            )
            Text("Jenis Obat")
            OutlinedTextField(
                value = jenis,
                onValueChange = { jenis = it },
                label = { Text("Masukkan Jenis Obat") }
            )
            Text("Dosis Obat")
            OutlinedTextField(
                value = dosis,
                onValueChange = { dosis = it },
                label = { Text("Masukkan Dosis Obat") }
            )
            Text("Gambar Obat")
            OutlinedTextField(
                value = gambar,
                onValueChange = { gambar = it },
                label = { Text("Masukkan gambar Obat") }
            )
            Text("Deskripsi Obat")
            OutlinedTextField(
                value = deskripsi,
                onValueChange = { deskripsi = it },
                label = { Text("Masukkan Deskripsi Obat") }
            )
        }
        Button(
            onClick = {
                if (nama.isBlank() || harga.isBlank() || jenis.isBlank() || dosis.isBlank() || deskripsi.isBlank()) {
                    Toast.makeText(context, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.createObat(
                        ObatRequest(
                            nama = nama,
                            harga = harga.toDoubleOrNull() ?: 0.0,
                            dosis = dosis,
                            jenis = jenis,
                            gambar = gambar,
                            deskripsi = deskripsi
                        )
                    )
                    Toast.makeText(context, "Data Obat berhasil dibuat", Toast.LENGTH_SHORT).show()
                    nama = ""
                    harga = ""
                    dosis = ""
                    jenis = ""
                    gambar = ""
                    deskripsi = ""
                    navController.navigate(Screen.ScreenAdmin.route){
                        popUpTo(Screen.ScreenAdmin.route){
                            inclusive = true
                        }
                    }
                }
            },
            modifier = Modifier.size(150.dp, 40.dp)
        ) {
            Text("Submit Obat")
        }
    }

    if (isLoading) {
        CircularProgressIndicator()
    }

    if (errorMessage.isNotEmpty()) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
