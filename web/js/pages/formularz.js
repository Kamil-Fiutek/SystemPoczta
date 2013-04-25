$(function() {
    
    //reloadTabs();
    
    $(".tabEvaluator").bind("click", function() {
        reloadTabs();
        
        $(this).parents(".tab").removeClass("tabActive");
        
        return false;
    });
    
});

function reloadTabs() {
    $(".tab").removeClass("tabActive");
    
    var value = $("select[name='typ_przesylki']").val();
    
    if(value == "1")
        $(".tab.tabList").addClass("tabActive");
    if(value == "2")
        $(".tab.tabPaczka").addClass("tabActive");
    if(value == "3")
        $(".tab.tabPrzekaz").addClass("tabActive");
}