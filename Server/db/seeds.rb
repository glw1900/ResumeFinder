
# ruby encoding: utf-8
project_list = [[1,"android",2015/05/10,2015/10/10,"android"],[1,"IR","2015/05/10","2015/10/10","IR"],1,"scalability",2015/05/10,2015/10/10,"scalability"]

project_list.each do |id, title,sd,ed,d|
  Project.create( appl_id: id, title: title, start_date: sd, end_date: ed, description: d )
end

5.times do |i|
  Experience.create(appl_id: 1, title: "the #{i}th experience", start_date: "2015/12/12", end_date: "2015/12/12", description: "haha")
  Education.create(appl_id: 1, school: "Brandeis", degree: "master", major: "cs", gpa: 3.9, description: "haha", start_date: "2015/12/12", end_date: "2015/12/12")
end
