package top.yogiczy.mytv.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import top.yogiczy.mytv.ui.rememberChildPadding
import top.yogiczy.mytv.ui.screens.home.components.HomeContent
import top.yogiczy.mytv.ui.screens.settings.SettingsState
import top.yogiczy.mytv.ui.screens.settings.rememberSettingsState
import top.yogiczy.mytv.ui.theme.MyTVTheme

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    homeScreeViewModel: HomeScreeViewModel = viewModel(),
    settingsState: SettingsState = rememberSettingsState(),
) {
    val uiState by homeScreeViewModel.uiState.collectAsState()

    CompositionLocalProvider(
        LocalDensity provides Density(
            density = LocalDensity.current.density * settingsState.uiDensityScaleRatio,
            fontScale = LocalDensity.current.fontScale * settingsState.uiFontScaleRatio,
        )
    ) {
        when (val s = uiState) {
            is HomeScreenUiState.Ready -> {
                HomeContent(
                    modifier = modifier,
                    iptvGroupList = s.iptvGroupList,
                    epgList = s.epgList,
                    onBackPressed = onBackPressed,
                    settingsState = settingsState,
                )
            }

            is HomeScreenUiState.Loading -> HomeScreenLoading(s.message)
            is HomeScreenUiState.Error -> HomeScreenError(s.message)
        }
    }
}

@Composable
private fun HomeScreenLoading(message: String?) {
    val childPadding = rememberChildPadding()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = childPadding.start, bottom = childPadding.bottom),
        ) {
            Text(
                text = "加载中...",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )

            if (message != null) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    modifier = Modifier.sizeIn(maxWidth = 500.dp),
                )
            }
        }
    }
}

@Preview(device = "id:Android TV (720p)")
@Composable
private fun HomeScreenLoadingPreview() {
    MyTVTheme {
        HomeScreenLoading("获取远程直播源(2/10)...")
    }
}

@Composable
private fun HomeScreenError(message: String?) {
    val childPadding = rememberChildPadding()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = childPadding.start, bottom = childPadding.bottom),
        ) {
            Text(
                text = "加载失败",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error,
            )

            if (message != null) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                    modifier = Modifier.sizeIn(maxWidth = 500.dp),
                )
            }
        }
    }
}

@Preview(device = "id:Android TV (720p)")
@Composable
private fun HomeScreenErrorPreview() {
    MyTVTheme {
        HomeScreenError("获取远程直播源失败，请检查网络连接")
    }
}


@Preview(device = "id:Android TV (720p)")
@Composable
private fun HomeScreenErrorLongPreview() {
    MyTVTheme {
        HomeScreenError(
            "Caused by: androidx.media3.datasource.HttpDataSource\$HttpDataSourceException:" + " java.io.IOException: unexpected end of stream on com.android.okhttp.Address@2f10c24d"
        )
    }
}
