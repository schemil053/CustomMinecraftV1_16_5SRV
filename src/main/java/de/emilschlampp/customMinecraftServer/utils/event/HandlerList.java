package de.emilschlampp.customMinecraftServer.utils.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerList {
    private final Map<SListener, List<Method>> invokeManager = new HashMap<>();

    public void addMethodToInvoke(SListener listener, Method method) {
        if (!invokeManager.containsKey(listener)) {
            invokeManager.put(listener, new ArrayList<>());
        }

        if (!invokeManager.containsKey(listener)) {
            throw new IllegalStateException("Error in listener. Check equals-method.");
        }

        invokeManager.get(listener).add(method);
    }

    public void remove(SListener listener) {
        invokeManager.remove(listener);
    }

    public void invoke(SEvent event) {
        for (Map.Entry<SListener, List<Method>> sCloudListenerListEntry : new ArrayList<>(invokeManager.entrySet())) {
            try {
                for (Method method : sCloudListenerListEntry.getValue()) {
                    try {
                        method.invoke(sCloudListenerListEntry.getKey(), event);
                    } catch (Throwable throwable) {
                        System.err.println("Error beim ausführen des Listeners "+sCloudListenerListEntry.getKey().getClass().getName()+":");
                        System.err.println("Methode: "+method.toGenericString());
                        throwable.printStackTrace();
                    }
                }
            } catch (Throwable ignored) {

            }
        }
    }
}
