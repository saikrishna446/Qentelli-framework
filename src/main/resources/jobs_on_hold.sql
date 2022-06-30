SELECT   DISTINCT
              c.user_concurrent_program_name,
              c.description,
              ROUND ( ( (SYSDATE - a.actual_start_date) * 24 * 60 * 60 / 60),
                     2)
                 AS process_time,
              a.request_id,
              a.parent_request_id,
              a.request_date,
              a.actual_start_date,
              a.actual_completion_date,
              (a.actual_completion_date - a.request_date) * 24 * 60 * 60
                 AS end_to_end,
              d.user_name,
              a.phase_code,
              a.status_code "Program Status",
              a.argument_text "Program Paramters"
       FROM   apps.fnd_concurrent_requests a,
              apps.fnd_concurrent_programs  b,
              apps.fnd_concurrent_programs_tl c,
              apps.fnd_user d
      WHERE       a.concurrent_program_id = b.concurrent_program_id
              AND b.concurrent_program_id = c.concurrent_program_id
              AND a.requested_by = d.user_id
              AND a.requested_start_date > SYSDATE
              AND a.hold_flag = 'Y'
   ORDER BY   process_time DESC