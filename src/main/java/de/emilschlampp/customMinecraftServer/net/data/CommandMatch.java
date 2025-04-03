package de.emilschlampp.customMinecraftServer.net.data;

public class CommandMatch {
    private String match;
    private boolean hasTooltip;
    private String tooltip;

    public CommandMatch(String match) {
        this(match, false, "");
    }
    public CommandMatch(String match, boolean hasTooltip, String tooltip) {
        this.match = match;
        this.hasTooltip = hasTooltip;
        this.tooltip = tooltip;
    }

    public CommandMatch() {
    }

    public String getMatch() {
        return match;
    }

    public CommandMatch setMatch(String match) {
        this.match = match;
        return this;
    }

    public boolean isHasTooltip() {
        return hasTooltip;
    }

    public CommandMatch setHasTooltip(boolean hasTooltip) {
        this.hasTooltip = hasTooltip;
        return this;
    }

    public String getTooltip() {
        return tooltip;
    }

    public CommandMatch setTooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }
}
