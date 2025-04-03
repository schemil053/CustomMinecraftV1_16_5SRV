package de.emilschlampp.customMinecraftServer.utils.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EventManager {
    private final Map<Class<?>, HandlerList> registered = new HashMap<>();

    public void register(SListener listener) {
        register(listener.getClass(), listener);
    }

    public void unregister(Class<? extends SEvent> cl) {
        registered.remove(cl);
    }

    public void unregister(SListener listener) {
        registered.forEach((a, b) -> {
            b.remove(listener);
        });
    }

    private void register(Class clazz, SListener listener) {
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isBridge() || m.isSynthetic()) {
                continue;
            }
            if (m.getAnnotation(SEventHandler.class) == null) {
                continue;
            }
            if (m.getParameterCount() != 1) {
                continue;
            }
            if (!SEvent.class.isAssignableFrom(m.getParameters()[0].getType())) {
                continue;
            }
            if (!m.getReturnType().equals(void.class)) {
                continue;
            }
            Class<?> event = m.getParameters()[0].getType();


            if (registered.containsKey(event)) {
                registered.get(event).addMethodToInvoke(listener, m);
            } else {
                try {
                    registered.put(event, new HandlerList());
                    if (registered.containsKey(event)) {
                        registered.get(event).addMethodToInvoke(listener, m);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        if (clazz.getSuperclass() != null) {
            register(clazz.getSuperclass(), listener);
        }
    }

    public void call(SEvent event) {
        if(registered.containsKey(event.getClass())) {
            registered.get(event.getClass()).invoke(event);
        } else {
            try {
                registered.put(event.getClass(), new HandlerList());
                if (registered.containsKey(event.getClass())) {
                    registered.get(event.getClass()).invoke(event);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
