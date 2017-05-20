package com.itmo.service;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject.Kind;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;

/**
 * {@code InMemoryJavaCompiler} can be used for compiling a {@link
 * CharSequence} to a {@code byte[]}.
 * <p>
 * The compiler will not use the file system at all, instead using a {@link
 * ByteArrayOutputStream} for storing the byte code. For the source code, any
 * kind of {@link CharSequence} can be used, e.g. {@link String}, {@link
 * StringBuffer} or {@link StringBuilder}.
 * <p>
 * The {@code InMemoryCompiler} can easily be used together with a {@code
 * ByteClassLoader} to easily compile and load source code in a {@link String}:
 * <p>
 * <pre>
 * {@code
 * import com.oracle.java.testlibrary.InMemoryJavaCompiler;
 * import com.oracle.java.testlibrary.ByteClassLoader;
 *
 * class Example {
 *     public static void main(String[] args) {
 *         String className = "Foo";
 *         String sourceCode = "public class " + className + " {" +
 *                             "    public void bar() {" +
 *                             "        System.out.println("Hello from bar!");" +
 *                             "    }" +
 *                             "}";
 *         byte[] byteCode = InMemoryJavaCompiler.compile(className, sourceCode);
 *         Class fooClass = ByteClassLoader.load(className, byteCode);
 *     }
 * }
 * }
 * </pre>
 */
public class InMemoryJavaCompiler {
    private static class MemoryJavaFileObject extends SimpleJavaFileObject {
        private final String className;
        private final CharSequence sourceCode;
        private final ByteArrayOutputStream byteCode;

        public MemoryJavaFileObject(String className, CharSequence sourceCode) {
            super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.className = className;
            this.sourceCode = sourceCode;
            this.byteCode = new ByteArrayOutputStream();
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return sourceCode;
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return byteCode;
        }

        public byte[] getByteCode() {
            return byteCode.toByteArray();
        }

        public String getClassName() {
            return className;
        }
    }

    private static class FileManagerWrapper extends ForwardingJavaFileManager {
        private MemoryJavaFileObject file;

        public FileManagerWrapper(MemoryJavaFileObject file) {
            super(getCompiler().getStandardFileManager(null, null, null));
            this.file = file;
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className,
                                                   Kind kind, FileObject sibling)
                throws IOException {
            if (!file.getClassName().equals(className)) {
                throw new IOException("Expected class with name " + file.getClassName() +
                        ", but got " + className);
            }
            return file;
        }
    }

    /**
     * Compiles the class with the given name and source code.
     *
     * @param className  The name of the class
     * @param sourceCode The source code for the class with name {@code className}
     * @return The resulting byte code from the compilation
     * @throws RuntimeException if the compilation did not succeed
     */
    public static byte[] compile(String className, CharSequence sourceCode) {
        MemoryJavaFileObject file = new MemoryJavaFileObject(className, sourceCode);
        CompilationTask task = getCompilationTask(file);

        if (!task.call()) {
            throw new RuntimeException("Could not compile " + className + " with source code " + sourceCode);
        }

        return file.getByteCode();
    }

    private static JavaCompiler getCompiler() {
        return ToolProvider.getSystemJavaCompiler();
    }

    private static CompilationTask getCompilationTask(MemoryJavaFileObject file) {
        return getCompiler().getTask(null, new FileManagerWrapper(file), null, null, null, Arrays.asList(file));
    }
}