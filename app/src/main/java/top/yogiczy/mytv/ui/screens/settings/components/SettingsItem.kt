package top.yogiczy.mytv.ui.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import top.yogiczy.mytv.ui.theme.MyTVTheme
import top.yogiczy.mytv.ui.utils.handleDPadKeyEvents

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String = "",
    value: String = "",
    description: String = "",
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    Card(
        modifier = modifier
            .width(190.dp)
            .height(90.dp)
            .focusRequester(focusRequester)
            .handleDPadKeyEvents(
                onSelect = {
                    focusRequester.requestFocus()
                    onClick()
                },
                onLongSelect = {
                    focusRequester.requestFocus()
                    onLongClick()
                },
            ),
        colors = CardDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
            contentColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = MaterialTheme.colorScheme.onBackground,
            focusedContentColor = MaterialTheme.colorScheme.background,
            pressedContentColor = MaterialTheme.colorScheme.background,
        ),
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                )

                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            Text(
                text = description,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 2,
            )
        }
    }
}

@Preview
@Composable
private fun SettingsItemPreview() {
    MyTVTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(Unit) { focusRequester.requestFocus() }

            SettingsItem(
                title = "开机自启",
                value = "启用",
                description = "App will launch on boot App will launch on bootl launch on boot",
            )

            SettingsItem(
                title = "开机自启",
                value = "启用",
                description = "App will launch on boot App will launch on boot",
                focusRequester = focusRequester,
            )
        }
    }
}