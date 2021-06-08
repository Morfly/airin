//package org.morfly.airin.sample.core.ui
//
//import android.content.Context
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.ui.platform.LocalContext
//import coil.ImageLoader
//import coil.request.Disposable
//import coil.request.ImageRequest
//import coil.target.Target
//import com.google.accompanist.coil.CoilPainterDefaults
//import com.google.accompanist.coil.LocalImageLoader
//
//
//@Composable
//fun ImagePreLoader(
//    key: Any?,
//    urls: () -> List<String>
//) {
//    val imageLoader = CoilPainterDefaults.defaultImageLoader()
//    val context = LocalContext.current
//
//    DisposableEffect(key) {
//        val targets = urls().map { url ->
//            imageLoader.preload(context, url)
//        }
//
//        onDispose {
//            targets.forEach(Disposable::dispose)
//        }
//    }
//}
//
//private fun ImageLoader.preload(context: Context, url: String): Disposable {
//    val request = ImageRequest.Builder(context)
//        .data(url)
//        .target(EmptyTarget())
//        .build()
//    return enqueue(request)
//}
//
//
//private class EmptyTarget : Target
