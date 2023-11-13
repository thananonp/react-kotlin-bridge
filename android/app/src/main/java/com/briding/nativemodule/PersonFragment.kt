package com.briding.nativemodule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@OptIn(ExperimentalMaterialApi::class)
class PersonFragment(
    private var viewModel: PersonBlueprint
) : Fragment() {
    val TAG = "PersonFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val isRefreshing by viewModel.isRefreshing.collectAsState()
                val pullRefreshState = rememberPullRefreshState(
                    refreshing = isRefreshing, onRefresh = viewModel.refresh
                )
                
                val lifecycle = rememberLifecycleEvent()
                
                LaunchedEffect(key1 = lifecycle){
                    when(lifecycle){
                        Lifecycle.Event.ON_CREATE -> {
                            Log.d(TAG, "onCreate")
                        }
                        Lifecycle.Event.ON_START -> {
                            Log.d(TAG, "onStart")
                        }
                        Lifecycle.Event.ON_RESUME -> {
                            Log.d(TAG, "onResume")
                        }
                        Lifecycle.Event.ON_PAUSE -> {
                            Log.d(TAG, "onPause")
                        }
                        Lifecycle.Event.ON_STOP -> {
                            Log.d(TAG, "onStop")
                        }
                        Lifecycle.Event.ON_DESTROY -> {
                            Log.d(TAG, "onDestroy")
                        }
                        Lifecycle.Event.ON_ANY -> {
                            Log.d(TAG, "onAny")
                        }
                    }
                }

                MaterialTheme {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState)

                    ) {
                        Column(
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        ) {
                            Button(onClick = viewModel.onAddNewPerson) {
                                Text("Add new person")
                            }
                            Button(onClick = viewModel.onDeleteAllPerson) {
                                Text("Delete All")
                            }
                            viewModel.people.collectAsState().value.forEach { person ->
                                Card(onClick = { viewModel.onPress(person) }) {
                                    Text(text = "${person.name} ${person.surname} \nage ${person.age}")
                                }
                            }
                        }

                        PullRefreshIndicator(
                            refreshing = isRefreshing,
                            state = pullRefreshState,
                            modifier = Modifier.align(Alignment.TopCenter),
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun rememberLifecycleEvent(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current): Lifecycle.Event {
    var state by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            state = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return state
}