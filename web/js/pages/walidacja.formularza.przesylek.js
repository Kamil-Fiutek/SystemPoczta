jQuery(function()
{
    
    jQuery("#imie_nazwisko_nadawcy").validate
    ({
            expression: "if (VAL.match(/^[A-ZŻŹĆŃÓŁĘĄŚ]{1}[a-zżźćńółęąś]{2,30} [A-ZŻŹĆŃÓŁĘĄŚ]{1}[a-zżźćńółęąś]{2,30}$/)) return true; else return false;",
            message: "Proszę podać poprawne imię i nazwisko"
    });
    
    jQuery("#adres_nadawcy").validate
    ({
            expression: "if (VAL.match(/^[1-9a-z]{0,1}[ A-ZŻŹĆŃÓŁĘĄŚa-zżźćńółęąś]{2,34} ([0-9]{1,3}[/][0-9]{1,3}|[0-9]{1,3}[a-z]{1}) [A-ZŻŹĆŃÓŁĘĄŚa-zżźćńółęąś ]{2,34}$/)) return true; else return false;",
            message: "Proszę podać poprawny adres"
    });

    jQuery("#kod_pocztowy_nadawcy").validate
    ({
            expression: "if (VAL.match(/^\\d{2}-\\d{3}$/)) return true; else return false;",
            message: "Proszę podać poprawny kod pocztowy"
    });
    
    jQuery("#imie_nazwisko_odbiorcy").validate
    ({
            expression: "if (VAL.match(/^[A-ZŻŹĆŃÓŁĘĄŚ]{1}[a-zżźćńółęąś]{2,30} [A-ZŻŹĆŃÓŁĘĄŚ]{1}[a-zżźćńółęąś]{2,30}$/)) return true; else return false;",
            message: "Proszę podać poprawne imię i nazwisko"
    });
    
    jQuery("#adres_odbiorcy").validate
    ({
            expression: "if (VAL.match(/^[1-9a-z]{0,1}[ A-ZŻŹĆŃÓŁĘĄŚa-zżźćńółęąś]{2,34} ([0-9]{1,3}[/][0-9]{1,3}|[0-9]{1,3}[a-z]{1}) [A-ZŻŹĆŃÓŁĘĄŚa-zżźćńółęąś ]{2,34}$/)) return true; else return false;",
            message: "Proszę podać poprawny adres"
    });

    jQuery("#kod_pocztowy_odbiorcy").validate
    ({
            expression: "if (VAL.match(/^\\d{2}-\\d{3}$/)) return true; else return false;",
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