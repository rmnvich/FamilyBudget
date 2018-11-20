package rmnvich.apps.familybudget.domain.di.base;


public interface BaseComponent<V> {
    void inject(V view);
}
