package com.romanyuk.ui.listeners;

import java.io.File;

/**
 * Коллбэк для операций с файлом.
 *
 * @author <a href="mailto:asromanyuk@edu.hse.ru"> Andrey Romanyuk</a>
 */
public interface OnFileAction {
    void onFileExport(final File file);

    void onFileImport(final File file);
}
