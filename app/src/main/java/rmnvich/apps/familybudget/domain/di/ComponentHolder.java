package rmnvich.apps.familybudget.domain.di;


import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import rmnvich.apps.familybudget.app.dagger.AppComponent;
import rmnvich.apps.familybudget.app.dagger.AppModule;
import rmnvich.apps.familybudget.app.dagger.DaggerAppComponent;
import rmnvich.apps.familybudget.domain.di.base.BaseComponent;
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder;
import rmnvich.apps.familybudget.domain.di.base.BaseModule;

public class ComponentHolder {

    private final Context mContext;

    @Inject
    Map<Class<?>, Provider<BaseComponentBuilder>> mBuilders;

    private Map<Class<?>, BaseComponent> mComponents;

    public ComponentHolder(Context context) {
        mContext = context;
    }

    public void init() {
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(mContext)).build();
        appComponent.injectComponentsHolder(this);
        mComponents = new HashMap<>();
    }

    public BaseComponent getComponent(Class<?> cls) {
        return getComponent(cls, null);
    }

    public BaseComponent getComponent(Class<?> cls, BaseModule module) {
        BaseComponent component = mComponents.get(cls);
        if (component == null) {
            BaseComponentBuilder builder = mBuilders.get(cls).get();
            if (module != null) {
                builder.module(module);
            }
            component = builder.build();
            mComponents.put(cls, component);
        }
        return component;
    }

    public void releaseComponent(Class<?> cls) {
        mComponents.put(cls, null);
    }
}
