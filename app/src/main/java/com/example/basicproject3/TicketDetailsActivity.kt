package com.example.basicproject3

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.basicproject3.data.Utils.Companion.formatDate
import com.example.basicproject3.data.model.Ticket
import com.example.basicproject3.databinding.ActivityTicketDetailsBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.launch

class TicketDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTicketDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val ticket = intent.getParcelableExtra<Ticket>("ticket")!!
        val qrImageView: ImageView = findViewById(R.id.imgViewQrCode)

        lifecycleScope.launch {
            val event = ticket.getEvent()
            if (event != null) {
                binding.eventOfTicket.txtEventTitle.text = event.title
                event.getImgUrl().addOnSuccessListener {
                    Glide.with(this@TicketDetailsActivity).load(it)
                        .into(binding.eventOfTicket.imgEvent)
                }
                binding.eventOfTicket.txtEventStartTime.text = formatDate(event.date_start)
                binding.eventOfTicket.itemEvent.setOnClickListener {
                    val intent = Intent(this@TicketDetailsActivity, EventActivity::class.java)
                    val bundle = Bundle().apply {
                        putParcelable("event", event)
                    }
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }
        }

        ticket.id?.let { generateQRCode(ticket.id!!, qrImageView) }
    }

    private fun generateQRCode(text: String, imageView: ImageView) {
        try {
            val writer = QRCodeWriter()
            val bitMatrix: BitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }

            imageView.setImageBitmap(bitmap)

        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}