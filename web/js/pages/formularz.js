$(function() 
{    
    $(".tabEvaluator").bind("click", function() 
    {
        reloadTabs();
        
        $(this).parents(".tab").removeClass("tabActive");
        
        return false;
    });
});

function reloadTabs() 
{
    var fadeTime = 1500;
    $(".tab").removeClass("tabActive");
    
    var value = $("select[name='typ_przesylki']").val();
    
    if(value == "1")
    {
        //$(".tab.tabList").addClass("tabActive");
        $(".tab.tabList").fadeIn(fadeTime);
        $(".tab.tabPaczka").fadeOut(fadeTime);
        $(".tab.tabPrzekaz").fadeOut(fadeTime);
    }
    if(value == "2")
    {
        //$(".tab.tabPaczka").addClass("tabActive");
        $(".tab.tabPaczka").fadeIn(fadeTime);
        $(".tab.tabList").fadeOut(fadeTime);
        $(".tab.tabPrzekaz").fadeOut(fadeTime);
    }
    if(value == "3")
    {
        //$(".tab.tabPrzekaz").addClass("tabActive");
        $(".tab.tabPrzekaz").fadeIn(fadeTime);
        $(".tab.tabList").fadeOut(fadeTime);
        $(".tab.tabPaczka").fadeOut(fadeTime);
    }   
}
