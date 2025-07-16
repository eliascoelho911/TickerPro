package com.eliascoelho911.ebookreader.ds.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eliascoelho911.ebookreader.ds.R
import com.eliascoelho911.ebookreader.ds.theme.EbookReaderTheme
import com.eliascoelho911.ebookreader.ds.theme.JosefinSansFontFamily

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    style: TextStyle = AppLogoDefaults.style,
    color: Color = LocalContentColor.current
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.app_name),
        style = style,
        color = color,
        maxLines = 1
    )
}

object AppLogoDefaults {
    val style @Composable get() = MaterialTheme.typography.headlineSmall.copy(fontFamily = JosefinSansFontFamily)
}

@Preview
@Composable
private fun AppLogoPreview() {
    EbookReaderTheme {
        Surface {
            AppLogo(
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}