package com.briding.nativemodule

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

class NativeModulePackage : ReactPackage {
    override fun createNativeModules(p0: ReactApplicationContext): MutableList<NativeModule> {
        return mutableListOf()
    }

    override fun createViewManagers(p0: ReactApplicationContext) =
        mutableListOf(PersonViewManager(p0))
}