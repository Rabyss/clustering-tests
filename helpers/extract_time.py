import json
import sys
from collections import defaultdict
from dateutil import parser
from datetime import datetime, timezone

def unix_time_millis(dt):
    epoch = datetime.utcfromtimestamp(0).replace(tzinfo=timezone.utc)
    return int((dt  - epoch).total_seconds() * 1000)


if len(sys.argv) < 2:
    sys.exit(1)

dump_filename = sys.argv[1]

with open(dump_filename, 'r') as dump_file:
    dump_data = json.loads(dump_file.read())

items_by_user = defaultdict(list)

for item in dump_data:
    if "userId" in item:
        items_by_user[item["userId"]].append(item)

for userId, items in items_by_user.items():
    with open(userId + "_time.data", "w+") as out:
        for item in items:
            date = parser.parse(item["createdTime"])
            out.write(str(unix_time_millis(date)) + "\n")
