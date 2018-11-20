package rmnvich.apps.familybudget.app.dagger;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import rmnvich.apps.familybudget.data.common.Constants;
import rmnvich.apps.familybudget.data.database.AppDatabase;
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder;
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl;
import rmnvich.apps.familybudget.data.repository.local.FileRepositoryImpl;
import rmnvich.apps.familybudget.data.repository.preferences.PreferencesRepositoryImpl;
import rmnvich.apps.familybudget.presentation.activity.dashboard.dagger.DashboardActivityComponent;
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivity;
import rmnvich.apps.familybudget.presentation.activity.login.dagger.LoginActivityComponent;
import rmnvich.apps.familybudget.presentation.activity.login.mvp.LoginActivity;
import rmnvich.apps.familybudget.presentation.activity.make.category.dagger.MakeCategoryActivityComponent;
import rmnvich.apps.familybudget.presentation.activity.make.category.mvp.MakeCategoryActivity;
import rmnvich.apps.familybudget.presentation.activity.register.dagger.RegisterActivityComponent;
import rmnvich.apps.familybudget.presentation.activity.register.mvp.RegisterActivity;
import rmnvich.apps.familybudget.presentation.fragment.categories.dagger.FragmentCategoriesComponent;
import rmnvich.apps.familybudget.presentation.fragment.categories.mvp.FragmentCategories;
import rmnvich.apps.familybudget.presentation.fragment.familymembers.dagger.FragmentFamilyMembersComponent;
import rmnvich.apps.familybudget.presentation.fragment.familymembers.mvp.FragmentFamilyMembers;

import static rmnvich.apps.familybudget.data.common.Constants.DATABASE_NAME;

@Module(subcomponents = {DashboardActivityComponent.class, LoginActivityComponent.class,
        RegisterActivityComponent.class, FragmentFamilyMembersComponent.class,
        MakeCategoryActivityComponent.class, FragmentCategoriesComponent.class})
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

    @PerApplication
    @Provides
    DatabaseRepositoryImpl provideDatabaseRepository(AppDatabase appDatabase) {
        return new DatabaseRepositoryImpl(appDatabase);
    }

    @PerApplication
    @Provides
    PreferencesRepositoryImpl providePreferencesRepository(SharedPreferences preferences) {
        return new PreferencesRepositoryImpl(preferences);
    }

    @PerApplication
    @Provides
    FileRepositoryImpl provideLocalRepository() {
        return new FileRepositoryImpl(mContext);
    }

    @Provides
    AppDatabase provideAppDatabase() {
        return Room.databaseBuilder(mContext, AppDatabase.class,
                DATABASE_NAME).build();
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return mContext.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @IntoMap
    @ClassKey(DashboardActivity.class)
    BaseComponentBuilder provideDashboardActivity(DashboardActivityComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(LoginActivity.class)
    BaseComponentBuilder provideLoginActivity(LoginActivityComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(RegisterActivity.class)
    BaseComponentBuilder provideRegisterActivity(RegisterActivityComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(FragmentFamilyMembers.class)
    BaseComponentBuilder provideFragmentFamilyMembers(FragmentFamilyMembersComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(FragmentCategories.class)
    BaseComponentBuilder provideFragmentCategories(FragmentCategoriesComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(MakeCategoryActivity.class)
    BaseComponentBuilder provideMakeCategoryActivity(MakeCategoryActivityComponent.Builder builder) {
        return builder;
    }
}
