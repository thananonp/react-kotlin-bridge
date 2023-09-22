package com.briding.nativemodule

import android.util.Log
import android.view.Choreographer
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.annotations.ReactPropGroup
import com.facebook.react.uimanager.events.RCTEventEmitter
import kotlinx.coroutines.flow.MutableStateFlow


class PersonViewManager(
    private val reactContext: ReactApplicationContext
) : ViewGroupManager<FrameLayout>() {
    private var propWidth: Int? = null
    private var propHeight: Int? = null
    private var persons: MutableStateFlow<MutableList<Person>> = MutableStateFlow(mutableListOf())

    override fun getName() = REACT_CLASS

    /**
     * Return a FrameLayout which will later hold the Fragment
     */
    override fun createViewInstance(reactContext: ThemedReactContext) = FrameLayout(reactContext)

    /**
     * Map the "create" command to an integer
     */
    override fun getCommandsMap() = mapOf("create" to COMMAND_CREATE)

    /**
     * Handle "create" command (called from JS) and call createFragment method
     */
    override fun receiveCommand(
        root: FrameLayout, commandId: String, args: ReadableArray?
    ) {
        super.receiveCommand(root, commandId, args)
        val reactNativeViewId = requireNotNull(args).getInt(0)

        when (commandId.toInt()) {
            COMMAND_CREATE -> createFragment(root, reactNativeViewId)
        }
    }

    @ReactPropGroup(names = ["width", "height"], customType = "Style")
    fun setStyle(view: FrameLayout, index: Int, value: Int) {
        if (index == 0) propWidth = value
        if (index == 1) propHeight = value
    }

    @ReactProp(name = "persons", customType = "Read")
    fun setPersons(view: FrameLayout, persons: ReadableArray) {
        Log.d(TAG, "setPersons: $persons")
        val personArrayList = persons.toArrayList()
        val list = personArrayList.map {
            if (it is HashMap<*, *>) {
                val name = (it["name"] ?: "") as String
                val surname = (it["surname"] ?: "") as String
                val age = (it["age"] as Double).toInt()
                Person(name, surname, age, listOf())
            } else {
                return
            }
        }
        this.persons.value = list.toMutableList()
    }

    /**
     * Replace your React Native view with a custom fragment
     */
    fun createFragment(root: FrameLayout, reactNativeViewId: Int) {
        val parentView = root.findViewById<ViewGroup>(reactNativeViewId)
        setupLayout(parentView)
        // Create React-side ViewModel
        val viewModel = ReactPersonViewModel()
        // Assign method and variable
        viewModel.people = persons
        viewModel.onPress = {
            // Create Arguments and pass it to React side
            val personObject = Arguments.createMap().apply {
                putString("name", it.name)
                putString("surname", it.surname)
                putInt("age", it.age)
            }
            reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit("personOnPress", personObject)
        }
        viewModel.onAddNewPerson = {
            reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit("personAddNew", null)
        }
        // Create and display fragment
        val myFragment = PersonFragment(viewModel)
        val activity = reactContext.currentActivity as FragmentActivity
        activity.supportFragmentManager.beginTransaction()
            .replace(reactNativeViewId, myFragment, reactNativeViewId.toString()).commit()
    }

    fun setupLayout(view: View) {
        Choreographer.getInstance().postFrameCallback(object : Choreographer.FrameCallback {
            override fun doFrame(frameTimeNanos: Long) {
                manuallyLayoutChildren(view)
                view.viewTreeObserver.dispatchOnGlobalLayout()
                Choreographer.getInstance().postFrameCallback(this)
            }
        })
    }

    /**
     * Layout all children properly
     */
    private fun manuallyLayoutChildren(view: View) {
        // propWidth and propHeight coming from react-native props
        val width = requireNotNull(propWidth)
        val height = requireNotNull(propHeight)

        view.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        )

        view.layout(0, 0, width, height)
    }

    companion object {
        private const val REACT_CLASS = "PersonViewManager"
        private const val COMMAND_CREATE = 1
        const val TAG = "PersonViewManager"
    }

    private class ReactPersonViewModel : PersonViewModel() {
        override var people: MutableStateFlow<MutableList<Person>> =
            MutableStateFlow(mutableListOf())
        override var onPress: (person: Person) -> Unit = {}
        override var onAddNewPerson: () -> Unit = {}
    }
}