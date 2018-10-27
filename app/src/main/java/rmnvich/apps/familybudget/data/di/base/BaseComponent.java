package rmnvich.apps.familybudget.data.di.base;


public interface BaseComponent<V> {
    void inject(V view);
}
