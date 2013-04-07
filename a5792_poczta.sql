-- phpMyAdmin SQL Dump
-- version 3.4.4
-- http://www.phpmyadmin.net
--
-- Host: mysql.futurehost.pl
-- Czas wygenerowania: 06 Kwi 2013, 16:52
-- Wersja serwera: 5.1.66
-- Wersja PHP: 5.3.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT=0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `a5792_poczta`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `gabaryty`
--

CREATE TABLE `gabaryty` (
  `idGabarytu` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `opisGabarytu` varchar(80) NOT NULL,
  `cena` decimal(6,2) NOT NULL,
  PRIMARY KEY (`idGabarytu`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=6 ;

--
-- Zrzut danych tabeli `gabaryty`
--

INSERT INTO `gabaryty` (`idGabarytu`, `opisGabarytu`, `cena`) VALUES(1, 'mieszcząca się w prostopadłościanie 10x10x5 cm', '0.00');
INSERT INTO `gabaryty` (`idGabarytu`, `opisGabarytu`, `cena`) VALUES(2, 'mieszcząca się w prostopadłościanie 20x20x10 cm', '1.50');
INSERT INTO `gabaryty` (`idGabarytu`, `opisGabarytu`, `cena`) VALUES(3, 'mieszcząca się w prostopadłościanie 40x40x20 cm', '4.00');
INSERT INTO `gabaryty` (`idGabarytu`, `opisGabarytu`, `cena`) VALUES(4, 'mieszcząca się w prostopadłościanie 60x60x60 cm', '6.50');
INSERT INTO `gabaryty` (`idGabarytu`, `opisGabarytu`, `cena`) VALUES(5, 'nie mieszcząca się w największym gabarycie', '10.00');

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `historiaPrzesylek`
--

CREATE TABLE `historiaPrzesylek` (
  `idWpisu` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `idPrzesylki` bigint(20) unsigned NOT NULL,
  `idPracownika` int(10) unsigned NOT NULL,
  `idStatusu` tinyint(3) unsigned NOT NULL,
  `czas` datetime NOT NULL,
  `opis` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idWpisu`),
  KEY `idPrzesylki` (`idPrzesylki`),
  KEY `idPracownika` (`idPracownika`),
  KEY `idStatusu` (`idStatusu`)
) ENGINE=MyISAM DEFAULT CHARSET=latin2 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `kraje`
--

CREATE TABLE `kraje` (
  `idKraju` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'numeryczny kod kraju',
  `kraj` varchar(40) NOT NULL,
  `cena` decimal(6,2) NOT NULL COMMENT 'Jakoś wlicza się do ceny przesyłki',
  PRIMARY KEY (`idKraju`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=617 ;

--
-- Zrzut danych tabeli `kraje`
--

INSERT INTO `kraje` (`idKraju`, `kraj`, `cena`) VALUES(616, 'Polska', '0.00');
INSERT INTO `kraje` (`idKraju`, `kraj`, `cena`) VALUES(250, 'Francja', '4.50');

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `lisy`
--

CREATE TABLE `lisy` (
  `idPrzesylki` bigint(20) unsigned NOT NULL,
  `priorytetowy` tinyint(1) NOT NULL COMMENT 'To odpowiada za boolean',
  `masa` float NOT NULL COMMENT 'Masa w kg. Istotna przy obliczaniu ceny przesłania paczki.',
  PRIMARY KEY (`idPrzesylki`)
) ENGINE=MyISAM DEFAULT CHARSET=latin2 COMMENT='W relacji jeden do jeden z przsyłkami';

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `logiOperacjiAdministracyjnych`
--

CREATE TABLE `logiOperacjiAdministracyjnych` (
  `idOperacji` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `idPracownika` int(10) unsigned NOT NULL,
  `idTypuOperacji` tinyint(3) unsigned NOT NULL,
  `opis` varchar(200) NOT NULL COMMENT 'ewentualny opis',
  PRIMARY KEY (`idOperacji`),
  KEY `idUzytkownika` (`idPracownika`)
) ENGINE=MyISAM DEFAULT CHARSET=latin2 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `paczki`
--

CREATE TABLE `paczki` (
  `idPrzesylki` bigint(20) unsigned NOT NULL,
  `priorytetowa` tinyint(1) NOT NULL COMMENT 'traktować jak bool',
  `masa` float NOT NULL COMMENT 'Masa w kg. Istotna przy obliczaniu ceny przesłania paczki.',
  `idGabarytu` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`idPrzesylki`),
  KEY `idGabarytu` (`idGabarytu`)
) ENGINE=MyISAM DEFAULT CHARSET=latin2 COMMENT='W relacji jeden do jeden z przsyłkami';

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `placowki`
--

CREATE TABLE `placowki` (
  `idPlacowki` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `adres` varchar(80) NOT NULL COMMENT 'Dokładny adres, czyli ew. ulica i ew. numer budynku/numer',
  `kod` varchar(6) NOT NULL,
  `idKraju` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`idPlacowki`),
  KEY `idKraju` (`idKraju`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=3 ;

--
-- Zrzut danych tabeli `placowki`
--

INSERT INTO `placowki` (`idPlacowki`, `adres`, `kod`, `idKraju`) VALUES(1, ' ul. Solec 15/1', '00-403', 616);
INSERT INTO `placowki` (`idPlacowki`, `adres`, `kod`, `idKraju`) VALUES(2, 'ul. Akademicka 1/1', '44-100', 616);

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `pracownicy`
--

CREATE TABLE `pracownicy` (
  `idPracownika` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idPlacowki` int(10) unsigned NOT NULL,
  `idPrzelozonego` int(10) unsigned DEFAULT NULL COMMENT 'Jeśli null to nie ma przełożonego',
  `imie` varchar(40) NOT NULL,
  `nazwisko` varchar(40) NOT NULL,
  `haslo` char(40) DEFAULT NULL COMMENT 'SHA-0 lub SHA-1 zapisane heksadecymalnie. Jeśli null to konto zablokowane.',
  `uprawnienia` char(10) NOT NULL COMMENT 'Każdy znak to jedno uprawnienie. Istotne jest przyporządkowanie uprawnień. Propozycja: dodanie stanu przesyłki, administracja całym systemem, administracja placówką, edycja historii operacji, ...',
  PRIMARY KEY (`idPracownika`),
  KEY `idPlacowki` (`idPlacowki`),
  KEY `idPrzelozonego` (`idPrzelozonego`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=3 ;

--
-- Zrzut danych tabeli `pracownicy`
--

INSERT INTO `pracownicy` (`idPracownika`, `idPlacowki`, `idPrzelozonego`, `imie`, `nazwisko`, `haslo`, `uprawnienia`) VALUES(1, 2, NULL, 'Jan', 'Kowalski', 'aaaaaaaaaaaaaaaaaaaaaaaaaa', '1111111111');
INSERT INTO `pracownicy` (`idPracownika`, `idPlacowki`, `idPrzelozonego`, `imie`, `nazwisko`, `haslo`, `uprawnienia`) VALUES(2, 1, 1, 'Adam', 'Nowak', NULL, '100000000');

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `przekazy`
--

CREATE TABLE `przekazy` (
  `idPrzesylki` bigint(20) unsigned NOT NULL,
  `kwota` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idPrzesylki`)
) ENGINE=MyISAM DEFAULT CHARSET=latin2 COMMENT='W relacji jeden do jeden z przsyłkami';

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `przesylki`
--

CREATE TABLE `przesylki` (
  `idPrzesylki` bigint(20) unsigned NOT NULL,
  `nadawca` varchar(80) NOT NULL COMMENT 'Czyli np imię i nazwisko, ew. nazwa firmy',
  `idKrajuNadawcy` smallint(5) unsigned NOT NULL,
  `kodNadawcy` varchar(6) NOT NULL,
  `dokladnyAdresNadawcy` varchar(80) NOT NULL COMMENT 'czyli ulica/aleja z numerem budynku ew. miszkania',
  `odbiorca` varchar(80) NOT NULL,
  `idKrajuOdbiorcy` smallint(5) unsigned NOT NULL,
  `kodOdbiorcy` varchar(6) NOT NULL,
  `dokladnyAdresOdbiorcy` varchar(80) NOT NULL COMMENT 'bez kodu pocztowego i kraju',
  PRIMARY KEY (`idPrzesylki`),
  KEY `idKrajuNadawcy` (`idKrajuNadawcy`),
  KEY `idKrajuOdbiorcy` (`idKrajuOdbiorcy`)
) ENGINE=MyISAM DEFAULT CHARSET=latin2 COMMENT='W relacji jeden do jeden z listami, paczkami i przesylkami. ';

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `statusyPrzesylek`
--

CREATE TABLE `statusyPrzesylek` (
  `idStatusuPrzesylki` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `statusPrzesylki` varchar(60) NOT NULL,
  PRIMARY KEY (`idStatusuPrzesylki`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=9 ;

--
-- Zrzut danych tabeli `statusyPrzesylek`
--

INSERT INTO `statusyPrzesylek` (`idStatusuPrzesylki`, `statusPrzesylki`) VALUES(1, 'Zarejestrowana');
INSERT INTO `statusyPrzesylek` (`idStatusuPrzesylki`, `statusPrzesylki`) VALUES(2, 'Wysłana');
INSERT INTO `statusyPrzesylek` (`idStatusuPrzesylki`, `statusPrzesylki`) VALUES(3, 'W trakcie transportu');
INSERT INTO `statusyPrzesylek` (`idStatusuPrzesylki`, `statusPrzesylki`) VALUES(4, 'Doręczano');
INSERT INTO `statusyPrzesylek` (`idStatusuPrzesylki`, `statusPrzesylki`) VALUES(5, 'Zwrot - brak możliwości doręczenia');
INSERT INTO `statusyPrzesylek` (`idStatusuPrzesylki`, `statusPrzesylki`) VALUES(6, 'Zwrot - odmowa przyjęcia przez odbiorce');
INSERT INTO `statusyPrzesylek` (`idStatusuPrzesylki`, `statusPrzesylki`) VALUES(7, 'Zwrócono');

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `typyOperacjiAdministracyjnych`
--

CREATE TABLE `typyOperacjiAdministracyjnych` (
  `idTypuOperacji` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `opis` varchar(80) NOT NULL,
  PRIMARY KEY (`idTypuOperacji`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=11 ;

--
-- Zrzut danych tabeli `typyOperacjiAdministracyjnych`
--

INSERT INTO `typyOperacjiAdministracyjnych` (`idTypuOperacji`, `opis`) VALUES(1, 'Dodanie placówki');
INSERT INTO `typyOperacjiAdministracyjnych` (`idTypuOperacji`, `opis`) VALUES(2, 'Usunięcie placówki');
INSERT INTO `typyOperacjiAdministracyjnych` (`idTypuOperacji`, `opis`) VALUES(3, 'Edycja placówki');
INSERT INTO `typyOperacjiAdministracyjnych` (`idTypuOperacji`, `opis`) VALUES(4, 'Dodanie pracownika');
INSERT INTO `typyOperacjiAdministracyjnych` (`idTypuOperacji`, `opis`) VALUES(5, 'Zmiana hasła pracownika');
INSERT INTO `typyOperacjiAdministracyjnych` (`idTypuOperacji`, `opis`) VALUES(6, 'Edycja danych pracownika');
INSERT INTO `typyOperacjiAdministracyjnych` (`idTypuOperacji`, `opis`) VALUES(7, 'Blokada pracownika');
INSERT INTO `typyOperacjiAdministracyjnych` (`idTypuOperacji`, `opis`) VALUES(8, 'Usunięcie pracownika');
INSERT INTO `typyOperacjiAdministracyjnych` (`idTypuOperacji`, `opis`) VALUES(9, 'Generowanie raportu');
INSERT INTO `typyOperacjiAdministracyjnych` (`idTypuOperacji`, `opis`) VALUES(10, 'Zmiana uprawnień pracownika');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
