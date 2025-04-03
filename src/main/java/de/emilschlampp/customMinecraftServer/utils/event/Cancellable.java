package de.emilschlampp.customMinecraftServer.utils.event;

public interface Cancellable {
    /**
     * Sorgt dafür, dass dieses Abgebrochen wird
     * @param cancel
     */
    public void setCancelled(boolean cancel);

    /**
     * Prüft, ob das Event abgebrochen ist
     * @return true, wenn das Event abgebrochen wird
     */
    public boolean isCancelled();
}
