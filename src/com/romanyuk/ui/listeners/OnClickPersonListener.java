package com.romanyuk.ui.listeners;

import com.romanyuk.domain.entity.Person;

/**
 * Слушатель для коллбэка с возвращением экземпляра Person.
 *
 * @author <a href="mailto:asromanyuk@edu.hse.ru"> Andrey Romanyuk</a>
 */
public interface OnClickPersonListener {
    void onClick(final Person person);
}
