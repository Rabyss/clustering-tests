import random
from datetime import datetime, timedelta, timezone

def unix_time_millis(dt):
    epoch = datetime.utcfromtimestamp(0).replace(tzinfo=timezone.utc)
    return int((dt  - epoch).total_seconds() * 1000)

for i in range(1000):
    step = timedelta(days=1)
    year = 2007 + i % 10
    start = datetime(year, 1, 1, tzinfo=timezone.utc)
    end = datetime(year + 1, 1, 1, tzinfo=timezone.utc)
    random_date = start + random.randrange((end - start) // step + 1) * step
    print(unix_time_millis(random_date))
