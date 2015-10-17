class Applicant < ActiveRecord::Base
  has_many :receives
  has_many :companies, :through => :receives
  has_many :projects
end
