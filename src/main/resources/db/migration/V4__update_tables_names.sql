alter table users
    rename column is_active to deleted;

alter table users
    rename to employees;

alter table employees
    alter column deleted set default false;

alter table drivers
    rename column user_id to employee_id;

alter table drivers
drop constraint drivers_user_id_fkey;

alter table drivers
    rename to driver_profile;

alter table driver_profile
    add foreign key (employee_id) references employees
        on update restrict;



alter table clients
    rename column is_active to deleted;

alter table clients
    alter column deleted set default false;

alter table vehicles
    rename column is_active to deleted;

alter table vehicles
    alter column deleted set default false;



alter table companies
    rename column is_active to deleted;

alter table companies
    alter column deleted set default false;


alter table deliveries_history
    rename to delivery_history;

