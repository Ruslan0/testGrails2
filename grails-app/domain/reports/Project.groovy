package reports

class Project {
    String name
    static constraints = {
        name blank: false, unique: true, maxSize: 50
    }
}
