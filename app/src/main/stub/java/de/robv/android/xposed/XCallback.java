package de.robv.android.xposed;
public abstract class XCallback implements Comparable<XCallback> {
    public int priority;
    public XCallback() { this.priority = 50; }
    public XCallback(int priority) { this.priority = priority; }
    @Override public int compareTo(XCallback other) { return 0; }
    public static final int PRIORITY_DEFAULT = 50;
}
