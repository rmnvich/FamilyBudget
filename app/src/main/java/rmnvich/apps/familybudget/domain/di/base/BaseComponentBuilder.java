package rmnvich.apps.familybudget.domain.di.base;


public interface BaseComponentBuilder<C extends BaseComponent, M extends BaseModule> {

    C build();

    BaseComponentBuilder<C, M> module(M module);
}
