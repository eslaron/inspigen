-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 24 Paź 2015, 00:52
-- Wersja serwera: 5.6.21
-- Wersja PHP: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `inspigen`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ig_addresses`
--

CREATE TABLE IF NOT EXISTS `ig_addresses` (
`id` bigint(20) NOT NULL,
  `address` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `city` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `zip_code` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `state` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `country` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `registered_address` tinyint(1) NOT NULL,
  `mail_address` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `ig_addresses`
--

INSERT INTO `ig_addresses` (`id`, `address`, `city`, `zip_code`, `state`, `country`, `registered_address`, `mail_address`) VALUES
(12, 'Podkarpacka 1', 'Rzeszów', '38-548', 'Podkarpackie', 'Polska', 1, 0),
(13, 'Podwiślańska 1', 'Poznań', '35-564', 'Podkarpackie', 'Polska', 0, 1),
(21, 'Wilanowska 15', 'Warszawa', '35-564', 'Mazowieckie', 'Polska', 0, 1),
(22, 'Krakowska 4', 'Kraków', '35-564', 'Dolno-Śląskie', 'Polska', 1, 1),
(24, 'Wilanowska 18', 'Kraków', '38-548', 'Podkarpackie', 'Polska', 1, 0),
(32, 'Mickiewicza 10', 'Rzeszów', '35-564', 'Podkarpackie', 'Polska', 0, 1),
(33, 'Markowska 7', 'Rzeszów', '35-564', 'Podkarpackie', 'Polska', 1, 0),
(34, 'Kopernika 10', 'Pozna?', '07-444', 'Pomorskie', 'Polska', 1, 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ig_attachments`
--

CREATE TABLE IF NOT EXISTS `ig_attachments` (
`id` bigint(20) NOT NULL,
  `file_name` varchar(70) COLLATE utf8_polish_ci NOT NULL,
  `file_type` varchar(80) COLLATE utf8_polish_ci NOT NULL,
  `file` longblob NOT NULL,
  `user_id` bigint(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ig_events`
--

CREATE TABLE IF NOT EXISTS `ig_events` (
`id` bigint(20) NOT NULL,
  `name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `type` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `description` varchar(500) COLLATE utf8_polish_ci DEFAULT NULL,
  `location_id` bigint(20) DEFAULT '0',
  `user_id` bigint(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ig_events_attachments`
--

CREATE TABLE IF NOT EXISTS `ig_events_attachments` (
`id` bigint(20) NOT NULL,
  `event_id` bigint(20) NOT NULL,
  `attachment_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ig_events_users`
--

CREATE TABLE IF NOT EXISTS `ig_events_users` (
`id` bigint(20) NOT NULL,
  `event_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ig_locations`
--

CREATE TABLE IF NOT EXISTS `ig_locations` (
`id` bigint(20) NOT NULL,
  `name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `type` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  `address` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `city` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `zip_code` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `state` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `country` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `phone` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `ig_locations`
--

INSERT INTO `ig_locations` (`id`, `name`, `type`, `address`, `city`, `zip_code`, `state`, `country`, `phone`, `email`) VALUES
(10, 'Politechnika Wroc?awska', 'Uczelnia wy?sza', 'Politechnicka', 'Wroc?aw', '50-555', 'Dolno?l?skie', 'Polska', NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ig_personal_data`
--

CREATE TABLE IF NOT EXISTS `ig_personal_data` (
`id` bigint(20) NOT NULL,
  `first_name` varchar(12) COLLATE utf8_polish_ci NOT NULL,
  `last_name` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `pesel` varchar(11) COLLATE utf8_polish_ci NOT NULL,
  `phone` varchar(11) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ig_personal_data_addresses`
--

CREATE TABLE IF NOT EXISTS `ig_personal_data_addresses` (
`id` bigint(20) NOT NULL,
  `personal_data_id` bigint(20) NOT NULL,
  `address_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ig_system_settings`
--

CREATE TABLE IF NOT EXISTS `ig_system_settings` (
`id` bigint(20) NOT NULL,
  `max_login_attempts` int(11) NOT NULL DEFAULT '3',
  `account_lock_time` int(11) NOT NULL DEFAULT '15',
  `url_expiration_time` int(11) NOT NULL DEFAULT '2880',
  `email_address` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  `email_host` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  `email_port` int(4) DEFAULT NULL,
  `email_username` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  `email_password` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `ig_system_settings`
--

INSERT INTO `ig_system_settings` (`id`, `max_login_attempts`, `account_lock_time`, `url_expiration_time`, `email_address`, `email_host`, `email_port`, `email_username`, `email_password`) VALUES
(1, 3, 15, 2880, 'system.inspigen@gmail.com', 'smtp.gmail.com', 587, 'system.inspigen', 'qwertyui7');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ig_users`
--

CREATE TABLE IF NOT EXISTS `ig_users` (
`id` bigint(20) NOT NULL,
  `username` varchar(12) COLLATE utf8_polish_ci NOT NULL,
  `password` varchar(32) COLLATE utf8_polish_ci NOT NULL,
  `email` varchar(40) COLLATE utf8_polish_ci NOT NULL,
  `role` varchar(10) COLLATE utf8_polish_ci NOT NULL DEFAULT 'ROLE_USER',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `account_non_locked` tinyint(1) NOT NULL DEFAULT '1',
  `account_non_expired` tinyint(1) DEFAULT '1',
  `credentials_non_expired` tinyint(1) DEFAULT '1',
  `password_token` varchar(16) COLLATE utf8_polish_ci NOT NULL,
  `password_token_expiration` timestamp NOT NULL DEFAULT '2014-01-01 00:01:01',
  `activation_token` varchar(16) COLLATE utf8_polish_ci DEFAULT NULL,
  `activation_token_expiration` timestamp NOT NULL DEFAULT '2014-01-01 00:01:01',
  `failed_logins` int(1) NOT NULL DEFAULT '0',
  `last_login_attempt` timestamp NULL DEFAULT '2014-01-01 00:01:01',
  `last_active` timestamp NULL DEFAULT NULL,
  `personal_data_id` bigint(20) DEFAULT NULL,
  `user_settings_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `ig_users`
--

INSERT INTO `ig_users` (`id`, `username`, `password`, `email`, `role`, `enabled`, `account_non_locked`, `account_non_expired`, `credentials_non_expired`, `password_token`, `password_token_expiration`, `activation_token`, `activation_token_expiration`, `failed_logins`, `last_login_attempt`, `last_active`, `personal_data_id`, `user_settings_id`) VALUES
(1, 'admin', 'c989e0f3b2dd29649dc98d6359c9bd4e', 'sebastian.sobiech@gmail.com', 'ROLE_ADMIN', 1, 1, 1, 1, 'ajguiedyb3cx90dk', '2015-02-04 07:37:12', 'tc1zpsdifsw65cxh', '2014-10-07 06:15:05', 0, '2015-01-23 15:33:09', '2015-01-23 15:33:09', NULL, NULL),
(2, 'michalo', '2dcef54132afe1b0627b62cbb5ab4b6f', 'michalo@inspigen.pl', 'ROLE_USER', 1, 1, 1, 1, 'wY9invSu5t8klZjV', '2015-10-14 12:03:53', 'FtscPKlckR6W7McN', '2015-10-14 12:03:53', 0, NULL, NULL, NULL, NULL),
(3, 'Adriano', '404e2ccd85d195cfe3beb40943a2be03', 'adriano@o2.pl', 'ROLE_USER', 1, 1, 1, 1, 'oEdJoqP97Mabxj3B', '2015-10-25 09:39:43', '9KrHP93dyTsH6PcL', '2015-10-25 09:40:11', 0, NULL, NULL, NULL, NULL),
(4, 'AdrianoMAno', '1a7f2d316e72ce5ffb52a519e5dfe7f7', 'qeeek@o2.pl', 'ROLE_USER', 1, 1, 1, 1, 'bxmJfM7COWGGlLpB', '2015-10-25 12:45:13', 'Yk4M6DGpe1rYxZZL', '2015-10-25 12:45:13', 0, NULL, NULL, NULL, NULL),
(5, 'Przemek', '2deee202517108c880538beb92e04146', 'przemek@o2.pl', 'ROLE_USER', 1, 1, 1, 1, 'jeetjlg8VGau4au8', '2015-10-25 12:48:43', 'uhHDhGGTJgtQABXv', '2015-10-25 12:48:43', 0, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ig_user_settings`
--

CREATE TABLE IF NOT EXISTS `ig_user_settings` (
`id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `ig_addresses`
--
ALTER TABLE `ig_addresses`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Indexes for table `ig_attachments`
--
ALTER TABLE `ig_attachments`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id_UNIQUE` (`id`), ADD KEY `fk_attachments_user_id_idx` (`user_id`);

--
-- Indexes for table `ig_events`
--
ALTER TABLE `ig_events`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id_UNIQUE` (`id`), ADD KEY `fk_events_location_id_idx` (`location_id`), ADD KEY `fk_events_user_id_idx` (`user_id`);

--
-- Indexes for table `ig_events_attachments`
--
ALTER TABLE `ig_events_attachments`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id_UNIQUE` (`id`), ADD KEY `fk_ea_event_id_idx` (`event_id`), ADD KEY `fk_ea_attachment_id_idx` (`attachment_id`);

--
-- Indexes for table `ig_events_users`
--
ALTER TABLE `ig_events_users`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id_UNIQUE` (`id`), ADD KEY `fk_eu_event_id_idx` (`event_id`), ADD KEY `fk_eu_user_id_idx` (`user_id`);

--
-- Indexes for table `ig_locations`
--
ALTER TABLE `ig_locations`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Indexes for table `ig_personal_data`
--
ALTER TABLE `ig_personal_data`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Indexes for table `ig_personal_data_addresses`
--
ALTER TABLE `ig_personal_data_addresses`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id_UNIQUE` (`id`), ADD KEY `fk_pda_personal_data_id_idx` (`personal_data_id`), ADD KEY `fk_pda_address_id_idx` (`address_id`);

--
-- Indexes for table `ig_system_settings`
--
ALTER TABLE `ig_system_settings`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Indexes for table `ig_users`
--
ALTER TABLE `ig_users`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id_UNIQUE` (`id`), ADD UNIQUE KEY `username` (`username`), ADD UNIQUE KEY `email` (`email`), ADD KEY `fk_users_personal_data_id_idx` (`personal_data_id`), ADD KEY `fk_users_user_settings_id_idx` (`user_settings_id`);

--
-- Indexes for table `ig_user_settings`
--
ALTER TABLE `ig_user_settings`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `ig_addresses`
--
ALTER TABLE `ig_addresses`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT dla tabeli `ig_attachments`
--
ALTER TABLE `ig_attachments`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `ig_events`
--
ALTER TABLE `ig_events`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `ig_events_attachments`
--
ALTER TABLE `ig_events_attachments`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `ig_events_users`
--
ALTER TABLE `ig_events_users`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `ig_locations`
--
ALTER TABLE `ig_locations`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT dla tabeli `ig_personal_data`
--
ALTER TABLE `ig_personal_data`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `ig_personal_data_addresses`
--
ALTER TABLE `ig_personal_data_addresses`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `ig_system_settings`
--
ALTER TABLE `ig_system_settings`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT dla tabeli `ig_users`
--
ALTER TABLE `ig_users`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT dla tabeli `ig_user_settings`
--
ALTER TABLE `ig_user_settings`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `ig_attachments`
--
ALTER TABLE `ig_attachments`
ADD CONSTRAINT `fk_attachments_user_id` FOREIGN KEY (`user_id`) REFERENCES `ig_users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ograniczenia dla tabeli `ig_events`
--
ALTER TABLE `ig_events`
ADD CONSTRAINT `fk_events_location_id` FOREIGN KEY (`location_id`) REFERENCES `ig_locations` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_events_user_id` FOREIGN KEY (`user_id`) REFERENCES `ig_users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ograniczenia dla tabeli `ig_events_attachments`
--
ALTER TABLE `ig_events_attachments`
ADD CONSTRAINT `fk_ea_attachment_id` FOREIGN KEY (`attachment_id`) REFERENCES `ig_attachments` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_ea_event_id` FOREIGN KEY (`event_id`) REFERENCES `ig_events` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ograniczenia dla tabeli `ig_events_users`
--
ALTER TABLE `ig_events_users`
ADD CONSTRAINT `fk_eu_event_id` FOREIGN KEY (`event_id`) REFERENCES `ig_events` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_eu_user_id` FOREIGN KEY (`user_id`) REFERENCES `ig_users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ograniczenia dla tabeli `ig_personal_data_addresses`
--
ALTER TABLE `ig_personal_data_addresses`
ADD CONSTRAINT `fk_pda_address_id` FOREIGN KEY (`address_id`) REFERENCES `ig_addresses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_pda_personal_data_id` FOREIGN KEY (`personal_data_id`) REFERENCES `ig_personal_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ograniczenia dla tabeli `ig_users`
--
ALTER TABLE `ig_users`
ADD CONSTRAINT `fk_users_personal_data_id` FOREIGN KEY (`personal_data_id`) REFERENCES `ig_personal_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_users_user_settings_id` FOREIGN KEY (`user_settings_id`) REFERENCES `ig_user_settings` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
