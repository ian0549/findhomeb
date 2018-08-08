package findhome.com.example.android.findhomeb

sealed class MyBoolKitchen {
    class Destination(val gowhere: String) : MyBoolKitchen()
    class Image(val url: String, val caption: String) : MyBoolKitchen()
    class Audio(val url: String, val duration: Int) : MyBoolKitchen()
}

