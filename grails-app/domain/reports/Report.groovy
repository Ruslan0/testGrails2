package reports

import org.grails.databinding.BindingFormat

class Report {
    @BindingFormat('dd.MM.yyyy')
    Date currdate
    Integer hours
    String note
    Assigned assigned

    static constraints = {
        currdate blank: false
        hours blank: false, max: 24, min: 0, validator:
                {
                    val, obj, errors ->
                        def prevHours = Report.withCriteria {
                            projections {
                                sum('hours')
                            }
                            and {
                                eq("currdate", obj.currdate)
                            }
                            and {
                                eq("assigned", obj.assigned)
                            }
                            and {
                                ne("id", obj.id ?: (Long) 0)
                            }
                        }
                        int allHours = val
                        if (prevHours[0])
                            allHours += prevHours[0]
                        if (allHours > 24) {
                            errors.reject('hours', 'The number of hours for this day can not exceed 24')
                        }
                }

        note blank: false, maxSize: 512
    }
}
