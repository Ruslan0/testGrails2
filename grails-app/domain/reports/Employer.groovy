package reports

class Employer {
    String login
    String password
    static hasMany = [assign: Assigned]

    static constraints = {
        login blank: false, unique: true, maxSize: 30
    }
}
