package com.example.uni_dubna.service.interfaces;

import com.example.uni_dubna.models.ScientificUser;

public interface ScientificUserService {

        /**
         * Регистрация нового пользователя.
         *
         * @param scientificUser пользователь для регистрации
         */
        void registerUser(ScientificUser scientificUser);

        /**
         * Проверяет, существует ли пользователь с данным именем пользователя.
         *
         * @param username имя пользователя для проверки
         * @return true, если пользователь существует, иначе false
         */
        boolean existsByUsername(String username);

        /**
         * Находит пользователя по имени пользователя.
         *
         * @param username имя пользователя
         * @return пользователь
         */
        ScientificUser findByUsername(String username);

        /**
         * Обновляет информацию пользователя.
         *
         * @param username имя пользователя для обновления
         * @param updatedUser обновленные данные пользователя
         */
        void updateUser(String username, ScientificUser updatedUser);

        // Добавьте другие методы по необходимости
}

