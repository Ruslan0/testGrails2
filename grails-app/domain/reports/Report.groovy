package reports

import org.grails.databinding.BindingFormat

class Report {
    @BindingFormat('dd.MM.yyyy')
    Date currdate
    Integer hours
    String note
    static belongsTo = [assigned: Assigned]

    static constraints = {
        currdate blank: false
        hours blank: false, max: 24, min: 0
        note blank: false, maxSize: 512
    }
}
