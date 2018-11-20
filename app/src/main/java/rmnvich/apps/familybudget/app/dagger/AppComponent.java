package rmnvich.apps.familybudget.app.dagger;


import dagger.Component;
import rmnvich.apps.familybudget.domain.di.ComponentHolder;

@PerApplication
@Component(modules = AppModule.class)
public interface AppComponent {
    void injectComponentsHolder(ComponentHolder holder);
}
