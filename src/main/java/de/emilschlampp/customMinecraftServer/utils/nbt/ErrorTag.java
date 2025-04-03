package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataOutputStream;

public class ErrorTag extends SpecificTag {
    public final String message;

    @Override
    public void write(DataOutputStream out) {
        throw new RuntimeException("Cannot write an error tag to NBT stream (" + getError() + ")");
    }

    public ErrorTag(String message) {
        this.message = message;
    }

    public String getError() {
        return message != null ? message : "";
    }

    @Override
    public String extraInfo() {
        return ": \"" + getError() + '"';
    }

    public String type() {
        return "Tag.Error";
    }

    @Override
    public int tagType() {
        throw new Error("Cannot write an error tag to NBT stream (" + getError() + ")");
    }

    @Override
    public String tagName() {
        return "Tag.Error";
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public boolean isError() {
        return true;
    }

    @Override
    public String error() {
        return getError();
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
