package de.jsauer.spring.views;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ReadOnlyHasValue;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HasUrlParameter;


public abstract class DetailPage<T> extends AbstractView implements HasUrlParameter<Long>, HasDynamicTitle {

    private final Binder<T> binder = new Binder<>();

    abstract void createBindings();

    void generateAndBindReadOnly(SerializableConsumer<T> valueProcessor) {
        ReadOnlyHasValue<T> readOnlyHasValue = new ReadOnlyHasValue<>(valueProcessor);
        binder.forField(readOnlyHasValue).bind(entity -> entity, null);
    }

    Binder<T> getBinder() {
        return binder;
    }
}
