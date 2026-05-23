package de.robv.android.xposed;

public class XCallback implements Comparable<XCallback> {
    public final int priority;
    public XCallback(int priority) { this.priority = priority; }
    public XCallback() { this(50); }
    @Override public int compareTo(XCallback other) { return other.priority - this.priority; }
}
