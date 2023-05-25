package com.example.demo.data;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.ToIntFunction;

public interface Repository {

    List<Film> getAll();

    Film getById(int id);

    boolean addFilm(Film car);

    boolean deleteFilm(int id);

    List<Film> getAllByActor(String actor);

    List<Film> getAllByFormat(String format);
}
