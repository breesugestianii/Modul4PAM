package com.example.pemesanantiket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pemesanantiket.ui.theme.PemesananTiketTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PemesananTiketTheme {
                TicketScreen()
            }
        }
    }
}

@Composable
fun TicketScreen() {

    var hargaTiket by remember { mutableStateOf(15000) }
    var jumlahTiket by remember { mutableStateOf(1) }
    var namaPembeli by remember { mutableStateOf("") }

    var status by remember {
        mutableStateOf("")
    }

    var isProcessing by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isProcessing) {
        if (isProcessing) {
            delay(5000)

            status = "Tiket telah dipesan"
            isProcessing = false
        }
    }

    TicketContent(
        hargaTiket = hargaTiket,
        jumlahTiket = jumlahTiket,
        namaPembeli = namaPembeli,
        status = status,

        onNamaChange = {
            namaPembeli = it
        },

        onKurangClick = {
            if (jumlahTiket > 1) {
                jumlahTiket--
            }
        },

        onTambahClick = {
            jumlahTiket++
        },

        onPesanClick = {
            if (namaPembeli.isBlank()) {
                status = "Nama masih kosong"
            } else {
                status = "Memproses pesanan..."
                isProcessing = true
            }
        }
    )
}


@Composable
fun TicketContent(
    hargaTiket: Int,
    jumlahTiket: Int,
    namaPembeli: String,
    status: String,
    onNamaChange: (String) -> Unit,
    onKurangClick: () -> Unit,
    onTambahClick: () -> Unit,
    onPesanClick: () -> Unit
) {

    val totalBayar = hargaTiket * jumlahTiket

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Pemesanan Tiket",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Text(
                    text = "Nama",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = namaPembeli,
                    onValueChange = onNamaChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Masukkan nama Anda")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Jumlah Tiket",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Button(
                        onClick = onKurangClick
                    ) {
                        Text("-")
                    }

                    Text(
                        text = "$jumlahTiket",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    Button(
                        onClick = onTambahClick
                    ) {
                        Text("+")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Harga: Rp$hargaTiket / tiket"
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Total: Rp$totalBayar",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onPesanClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pesan Tiket")
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (status.isNotEmpty()) {
                    Text(
                        text = "Status: $status",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
