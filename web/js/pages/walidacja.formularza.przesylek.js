jQuery(function()
{
    
    jQuery("#imie_nazwisko_nadawcy").validate
    ({
            expression: "if (VAL.match(/^[A-ZŻŹĆŃÓŁĘĄŚ]{1}[a-zżźćńółęąś]{2,30} [A-ZŻŹĆŃÓŁĘĄŚ]{1}[a-zżźćńółęąś]{2,30}$/)) return true; else return false;",
            message: "Proszę podać poprawne imię i nazwisko"
    });
    
    jQuery("#adres_nadawcy").validate
    ({
            expression: "if (VAL.match(/^[A-ZŻŹĆŃÓŁĘĄŚa-zżźćńółęąś1-9/]$/)) return true; else return false;",
            message: "Proszę podać poprawny adres"
    });

    jQuery("#kod_pocztowy_nadawcy").validate
    ({
            expression: "if (VAL.match(/^[1-9]{2}[-]{1}[1-9]{3}$/)) return true; else return false;",
            message: "Proszę podać poprawny kod pocztowy"
    });
    
    jQuery("#imie_nazwisko_odbiorcy").validate
    ({
            expression: "if (VAL.match(/^[A-ZŻŹĆŃÓŁĘĄŚ]{1}[a-zżźćńółęąś]{2,30} [A-ZŻŹĆŃÓŁĘĄŚ]{1}[a-zżźćńółęąś]{2,30}$/)) return true; else return false;",
            message: "Proszę podać poprawne imię i nazwisko"
    });
    
    jQuery("#adres_odbiorcy").validate
    ({
            expression: "if (VAL.match(/^[A-ZŻŹĆŃÓŁĘĄŚa-zżźćńółęąś1-9/]$/)) return true; else return false;",
            message: "Proszę podać poprawny adres"
    });

    jQuery("#kod_pocztowy_odbiorcy").validate
    ({
            expression: "if (VAL.match(/^[1-9]{2}[-]{1}[1-9]{3}$/)) return true; else return false;",
            message: "Proszę podać poprawny kod pocztowy"
    });

    jQuery("#masa_listu").validate
    ({
            expression: "if (VAL.match(/^[0-9]$/)) return true; else return false;",
            message: "Proszę podać poprawną masę listu"
    });
	
    jQuery("#masa_paczki").validate
    ({
            expression: "if (VAL.match(/^[0-9]$/)) return true; else return false;",
            message: "Proszę podać poprawną masę paczki"
    });

    jQuery("#kwota_przekazu").validate
    ({
            expression: "if (VAL.match(/^[0-9]$/)) return true; else return false;",
            message: "Proszę podać poprawną kwotę przekazu"
    });
});