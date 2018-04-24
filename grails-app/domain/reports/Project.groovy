package reports

class Project {
    String name
    static hasMany = [assign: Assigned]
    static constraints = {
        name blank: false, unique: true, maxSize: 50
    }
}
