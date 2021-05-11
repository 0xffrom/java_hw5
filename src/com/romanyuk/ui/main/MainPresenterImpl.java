package com.romanyuk.ui.main;

import com.romanyuk.domain.entity.Person;
import com.romanyuk.domain.entity.PersonWrapper;
import com.romanyuk.domain.repositories.PersonRepository;
import com.romanyuk.ui.main.utils.PersonSerializer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Здесь бы RxJava заиспользовать, было бы очень приятно на душе.
 */
public class MainPresenterImpl implements MainPresenter {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final PersonRepository personRepository;
    private final MainView mainView;

    public MainPresenterImpl(final MainView mainView, final PersonRepository personRepository) {
        this.mainView = mainView;
        this.personRepository = personRepository;
    }

    @Override
    public void loadPersons() {
        mainView.setPeople(personRepository.getPersons());
    }

    @Override
    public void importFile(final File file, final List<Person> personUiList) {
        PersonWrapper personWrapper = PersonSerializer.deserialize(file);

        // Если ошибка при чтении:
        if (personWrapper == null) {
            mainView.failedOperation("Произошла ошибка при импорте файла.");
            return;
        }

        List<Person> newPersons = personWrapper.getPersons();

        // Отбираем только тех, у кого ФИО уже не содержится в списке:
        List<Person> errorPersons = newPersons.stream()
                .filter(personUiList::contains)
                .collect(Collectors.toList());

        List<Person> insertionPersons = newPersons.stream()
                .filter(person -> !errorPersons.contains(person))
                .collect(Collectors.toList());

        personRepository.savePersons(insertionPersons);
        mainView.successImportPeople(insertionPersons);
        mainView.showFailedPeopleAfterImport(errorPersons);
    }

    @Override
    public void exportFile(final File file, final List<Person> personList) {
        executor.submit(() -> {
            PersonWrapper personWrapper = new PersonWrapper(new ArrayList<>(personList));
            PersonSerializer.serialize(personWrapper, file);
        });
    }

    @Override
    public void addPerson(final Person person, final List<Person> personUiList) {
        if (!personUiList.contains(person)) {
            executor.submit(() -> personRepository.savePerson(person));
            mainView.addPeople(person);
        } else {
            mainView.failedOperation("Контакт с таким именем уже существует.");
        }
    }

    @Override
    public void removePerson(final Person person) {
        executor.submit(() -> personRepository.removePerson(person));
    }

    @Override
    public void updatePerson(final Person person) {
        executor.submit(() -> personRepository.updatePerson(person));
    }
}
