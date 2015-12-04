class Company < ActiveRecord::Base
  has_many :receives
  has_many :applicants, :through => :receives
end
