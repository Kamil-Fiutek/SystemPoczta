SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE DATABASE IF NOT EXISTS `poczta` ;
USE `poczta` ;


-- -----------------------------------------------------
-- Table `poczta`.`gabaryty`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `poczta`.`gabaryty` (
  `idGabarytu` TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `opisGabarytu` VARCHAR(80) NOT NULL ,
  `cena` DECIMAL(6,2) NOT NULL ,
  PRIMARY KEY (`idGabarytu`) )
ENGINE = MyISAM
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = latin2;


-- -----------------------------------------------------
-- Table `poczta`.`kraje`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `poczta`.`kraje` (
  `idKraju` SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'numeryczny kod kraju' ,
  `kraj` VARCHAR(40) NOT NULL ,
  `cena` DECIMAL(6,2) NOT NULL COMMENT 'Jakoś wlicza się do ceny przesyłki' ,
  PRIMARY KEY (`idKraju`) )
ENGINE = MyISAM
AUTO_INCREMENT = 617
DEFAULT CHARACTER SET = latin2;


-- -----------------------------------------------------
-- Table `poczta`.`przesylki`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `poczta`.`przesylki` (
  `idPrzesylki` BIGINT(20) UNSIGNED NOT NULL ,
  `nadawca` VARCHAR(80) NOT NULL COMMENT 'Czyli np imię i nazwisko, ew. nazwa firmy' ,
  `idKrajuNadawcy` SMALLINT(5) UNSIGNED NOT NULL ,
  `kodPocztowyNadawcy` VARCHAR(6) NOT NULL ,
  `dokladnyAdresNadawcy` VARCHAR(80) NOT NULL COMMENT 'czyli ulica/aleja z numerem budynku ew. miszkania' ,
  `odbiorca` VARCHAR(80) NOT NULL ,
  `idKrajuOdbiorcy` SMALLINT(5) UNSIGNED NOT NULL ,
  `kodPocztowyOdbiorcy` VARCHAR(6) NOT NULL ,
  `dokladnyAdresOdbiorcy` VARCHAR(80) NOT NULL COMMENT 'bez kodu pocztowego i kraju' ,
  `dataZdefiniowania` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`idPrzesylki`) ,
  INDEX `idKrajuNadawcy` (`idKrajuNadawcy` ASC) ,
  INDEX `idKrajuOdbiorcy` (`idKrajuOdbiorcy` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin2
COMMENT = 'W relacji jeden do jeden z listami, paczkami i przesylkami. ';


-- -----------------------------------------------------
-- Table `poczta`.`placowki`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `poczta`.`placowki` (
  `idPlacowki` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `adres` VARCHAR(80) NOT NULL COMMENT 'Dokładny adres, czyli ew. ulica i ew. numer budynku/numer' ,
  `kod` VARCHAR(6) NOT NULL ,
  `idKraju` SMALLINT(5) UNSIGNED NOT NULL ,
  PRIMARY KEY (`idPlacowki`) ,
  INDEX `idKraju` (`idKraju` ASC) )
ENGINE = MyISAM
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin2;


-- -----------------------------------------------------
-- Table `poczta`.`pracownicy`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `poczta`.`pracownicy` (
  `idPracownika` INT(10) UNSIGNED NOT NULL,
  `idPlacowki` INT(10) UNSIGNED NOT NULL ,
  `idPrzelozonego` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT 'Jeśli null to nie ma przełożonego' ,
  `imie` VARCHAR(40) NOT NULL ,
  `nazwisko` VARCHAR(40) NOT NULL ,
  `haslo` CHAR(40) NULL DEFAULT NULL COMMENT 'SHA-0 lub SHA-1 zapisane heksadecymalnie. Jeśli null to konto zablokowane.' ,
  `uprawnienia` CHAR(10) NOT NULL COMMENT 'Każdy znak to jedno uprawnienie. Istotne jest przyporządkowanie uprawnień. Propozycja: dodanie stanu przesyłki, administracja całym systemem, administracja placówką, edycja historii operacji, ...' ,
  PRIMARY KEY (`idPracownika`) ,
  INDEX `idPlacowki` (`idPlacowki` ASC) ,
  INDEX `idPrzelozonego` (`idPrzelozonego` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin2;


-- -----------------------------------------------------
-- Table `poczta`.`statusyPrzesylek`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `poczta`.`statusyPrzesylek` (
  `idStatusuPrzesylki` TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `statusPrzesylki` VARCHAR(60) NOT NULL ,
  PRIMARY KEY (`idStatusuPrzesylki`) )
ENGINE = MyISAM
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = latin2;


-- -----------------------------------------------------
-- Table `poczta`.`historiaPrzesylek`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `poczta`.`historiaPrzesylek` (
  `idWpisu` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `idPrzesylki` BIGINT(20) UNSIGNED NOT NULL ,
  `idPracownika` INT(10) UNSIGNED NOT NULL ,
  `idStatusu` TINYINT(3) UNSIGNED NOT NULL ,
  `czas` DATETIME NOT NULL ,
  `opis` VARCHAR(200) NULL DEFAULT NULL ,
  PRIMARY KEY (`idWpisu`) ,
  INDEX `idPrzesylki` (`idPrzesylki` ASC) ,
  INDEX `idPracownika` (`idPracownika` ASC) ,
  INDEX `idStatusu` (`idStatusu` ASC) )
ENGINE = MyISAM
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin2;


-- -----------------------------------------------------
-- Table `poczta`.`lisy`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `poczta`.`lisy` (
  `idPrzesylki` BIGINT(20) UNSIGNED NOT NULL ,
  `priorytetowy` TINYINT(1) NOT NULL COMMENT 'To odpowiada za boolean' ,
  `masa` FLOAT NOT NULL COMMENT 'Masa w kg. Istotna przy obliczaniu ceny przesłania paczki.' ,
  PRIMARY KEY (`idPrzesylki`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin2
COMMENT = 'W relacji jeden do jeden z przsyłkami';


-- -----------------------------------------------------
-- Table `poczta`.`typyOperacjiAdministracyjnych`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `poczta`.`typyOperacjiAdministracyjnych` (
  `idTypuOperacji` TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `opis` VARCHAR(80) NOT NULL ,
  PRIMARY KEY (`idTypuOperacji`) )
ENGINE = MyISAM
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = latin2;


-- -----------------------------------------------------
-- Table `poczta`.`logiOperacjiAdministracyjnych`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `poczta`.`logiOperacjiAdministracyjnych` (
  `idOperacji` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `idPracownika` INT(10) UNSIGNED NOT NULL ,
  `idTypuOperacji` TINYINT(3) UNSIGNED NOT NULL ,
  `opis` VARCHAR(200) NOT NULL COMMENT 'ewentualny opis' ,
  PRIMARY KEY (`idOperacji`) ,
  INDEX `idUzytkownika` (`idPracownika` ASC) ,
  INDEX `fk_logiOperacjiAdministracyjnych_typyOperacjiAdministracyjn_idx` (`idTypuOperacji` ASC) )
ENGINE = MyISAM
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin2;


-- -----------------------------------------------------
-- Table `poczta`.`paczki`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `poczta`.`paczki` (
  `idPrzesylki` BIGINT(20) UNSIGNED NOT NULL ,
  `priorytetowa` TINYINT(1) NOT NULL COMMENT 'traktować jak bool' ,
  `masa` FLOAT NOT NULL COMMENT 'Masa w kg. Istotna przy obliczaniu ceny przesłania paczki.' ,
  `idGabarytu` TINYINT(3) UNSIGNED NOT NULL ,
  PRIMARY KEY (`idPrzesylki`) ,
  INDEX `idGabarytu` (`idGabarytu` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin2
COMMENT = 'W relacji jeden do jeden z przsyłkami';


-- -----------------------------------------------------
-- Table `poczta`.`przekazy`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `poczta`.`przekazy` (
  `idPrzesylki` BIGINT(20) UNSIGNED NOT NULL ,
  `kwota` DECIMAL(10,2) NOT NULL ,
  PRIMARY KEY (`idPrzesylki`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin2
COMMENT = 'W relacji jeden do jeden z przsyłkami';


--
-- Zrzut danych tabeli `gabaryty`
--

INSERT INTO `gabaryty` (`idGabarytu`, `opisGabarytu`, `cena`) VALUES(1, 'mieszcząca się w prostopadłościanie 10x10x5 cm', '0.00');
INSERT INTO `gabaryty` (`idGabarytu`, `opisGabarytu`, `cena`) VALUES(2, 'mieszcząca się w prostopadłościanie 20x20x10 cm', '1.50');
INSERT INTO `gabaryty` (`idGabarytu`, `opisGabarytu`, `cena`) VALUES(3, 'mieszcząca się w prostopadłościanie 40x40x20 cm', '4.00');
INSERT INTO `gabaryty` (`idGabarytu`, `opisGabarytu`, `cena`) VALUES(4, 'mieszcząca się w prostopadłościanie 60x60x60 cm', '6.50');
INSERT INTO `gabaryty` (`idGabarytu`, `opisGabarytu`, `cena`) VALUES(5, 'nie mieszcząca się w największym gabarycie', '10.00');

--
-- Zrzut danych tabeli `kraje`
--

INSERT INTO `kraje` (`idKraju`, `kraj`, `cena`) VALUES(616, 'Polska', '0.00');
INSERT INTO `kraje` (`idKraju`, `kraj`, `cena`) VALUES(250, 'Francja', '4.50');

--
-- Zrzut danych tabeli `placowki`
--

INSERT INTO `placowki` (`idPlacowki`, `adres`, `kod`, `idKraju`) VALUES(1, ' ul. Solec 15/1', '00-403', 616);
INSERT INTO `placowki` (`idPlacowki`, `adres`, `kod`, `idKraju`) VALUES(2, 'ul. Akademicka 1/1', '44-100', 616);
--
-- Zrzut danych tabeli `pracownicy`
--

INSERT INTO `pracownicy` (`idPracownika`, `idPlacowki`, `idPrzelozonego`, `imie`, `nazwisko`, `haslo`, `uprawnienia`) VALUES(1, 2, NULL, 'Jan', 'Kowalski', SHA1('admin1'), '1111111111');
INSERT INTO `pracownicy` (`idPracownika`, `idPlacowki`, `idPrzelozonego`, `imie`, `nazwisko`, `haslo`, `uprawnienia`) VALUES(2, 1, 1, 'Adam', 'Nowak', NULL, '100000000');
INSERT INTO `pracownicy` (`idPracownika`, `idPlacowki`, `idPrzelozonego`, `imie`, `nazwisko`, `haslo`, `uprawnienia`) VALUES(3, 2, NULL, 'Mieczysław', 'Kowalski', SHA1('admin1'), '1111111111');

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



USE `poczta` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
