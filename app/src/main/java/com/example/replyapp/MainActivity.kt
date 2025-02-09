package com.example.replyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.replyapp.data.local.LocalEmailsDataProvider
import com.example.replyapp.model.ReplyHomeUIState
import com.example.replyapp.model.ReplyHomeViewModel
import com.example.replyapp.ui.theme.ReplyAppTheme
import com.google.accompanist.adaptive.calculateDisplayFeatures

class MainActivity : ComponentActivity() {

    private val viewModel: ReplyHomeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReplyAppTheme {
                val windowSize = calculateWindowSizeClass(this)
                val displayFeatures = calculateDisplayFeatures(this)
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                ReplyApp(
                    windowSize = windowSize,
                    displayFeatures = displayFeatures,
                    replyHomeUIState = uiState,
                    closeDetailScreen = {
                        viewModel.closeDetailScreen()
                    },
                    navigateToDetail = { emailId, contentType ->
                        viewModel.setOpenedEmail(emailId, contentType)
                    },
                    toggleSelectedEmail = { emailId ->
                        viewModel.toggleSelectedEmail(emailId)
                    }
                )
            }
        }
    }
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Preview(showBackground = true)
    @Composable
    fun ReplyAppPreview() {
        ReplyAppTheme {
            ReplyApp(
                replyHomeUIState = ReplyHomeUIState(emails = LocalEmailsDataProvider.allEmails),
                windowSize = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp)),
                displayFeatures = emptyList(),
            )
        }
    }

}
