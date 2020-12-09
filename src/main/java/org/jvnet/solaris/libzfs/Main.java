package org.jvnet.solaris.libzfs;

/**
 * A test program.
 *
 * @author Kohsuke Kawaguchi
 */
public class Main {
    public static void main(String[] args) {
        LibZFS zfs;
        try {
            zfs = new LibZFS();
        } catch (Throwable e) {
            System.out.println("Aborted because " + e.toString());
            return;
        }
        System.out.println("userland version: " + zfs.getUserlandVersion());
        System.out.println("kernel version: " + zfs.getKernelVersion());
        for (ZFSFileSystem fs : zfs.roots()) {
            walkFs(fs, "");
        }
    }

    private static void walkFs(ZFSFileSystem fs, String prefix) {
        System.out.println(prefix + fs.getName());
        for (ZFSSnapshot s : fs.snapshots()) {
            System.out.println(prefix + " - " + s.getName());
        }
        for (ZFSFileSystem c : fs.children(ZFSFileSystem.class)) {
            walkFs(c, prefix + " ");
        }
    }
}
