package rmnvich.apps.familybudget.app.dagger;


import android.content.Context;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import rmnvich.apps.familybudget.data.di.base.BaseComponentBuilder;
import rmnvich.apps.familybudget.presentation.activity.dashboard.DashboardActivity;
import rmnvich.apps.familybudget.presentation.activity.dashboard.dagger.DashboardActivityComponent;

@Module(subcomponents = {DashboardActivityComponent.class})
public class AppModule {

    private final Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @PerApplication
    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    @IntoMap
    @ClassKey(DashboardActivity.class)
    BaseComponentBuilder provideDashboardActivity(DashboardActivityComponent.Builder builder) {
        return builder;
    }
}
